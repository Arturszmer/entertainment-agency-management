package com.agency.organizer.service;

import com.agency.auth.RoleType;
import com.agency.contractmanagement.constant.OrganizerLogMessage;
import com.agency.dto.organizer.OrganizerCreate;
import com.agency.dto.organizer.OrganizerDto;
import com.agency.dto.organizer.OrganizerNotesEdit;
import com.agency.exception.AgencyException;
import com.agency.organizer.assembler.OrganizerAssembler;
import com.agency.organizer.model.Organizer;
import com.agency.organizer.repository.OrganizerRepository;
import com.agency.service.OrganizerService;
import com.agency.user.helper.SecurityContextUsers;
import com.agency.user.model.UserProfile;
import com.agency.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.agency.exception.OrganizerErrorResult.*;
import static com.agency.organizer.assembler.OrganizerAssembler.toDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizerServiceImpl implements OrganizerService {

    private final OrganizerRepository repository;
    private final SecurityContextUsers contextUsers;
    private final UserProfileRepository userProfileRepository;

    @Override
    public OrganizerDto add(OrganizerCreate organizerDto) {
        Organizer organizer = repository.save(Organizer.createOrganizer(organizerDto));
        log.info(OrganizerLogMessage.SUCCESSFULLY_CREATED, organizer.getPublicId());

        return toDto(organizer);
    }

    @Override
    public OrganizerDto edit(String publicId, OrganizerCreate organizerDto) {
        Organizer organizer = repository.findOrganizerByPublicId(UUID.fromString(publicId))
                .orElseThrow(() -> new AgencyException(ORGANIZER_NOT_FOUND));
        validateUserToMakeChanges(organizer);
        organizer.updateOrganizer(organizerDto);
        log.info(OrganizerLogMessage.SUCCESSFULLY_UPDATED, publicId);
        return OrganizerAssembler.toDto(repository.save(organizer));
    }

    @Override
    public String editNotes(OrganizerNotesEdit notesEdit) {
        Organizer organizer = repository.findOrganizerByPublicId(UUID.fromString(notesEdit.publicId()))
                .orElseThrow(() -> new AgencyException(ORGANIZER_NOT_FOUND));
        validateUserToMakeChanges(organizer);
        organizer.setNotes(notesEdit.notes());
        return repository.save(organizer).getNotes();
    }

    @Override
    public void delete(String publicId) {
        repository.findOrganizerByPublicId(UUID.fromString(publicId)).ifPresent(organizer -> {
            validateUserToMakeChanges(organizer);
            repository.delete(organizer);
            log.info(OrganizerLogMessage.SUCCESSFULLY_DELETED, publicId);
        });
    }

    @Override
    public void assignUser(String publicId, String username) {
        Organizer organizer = repository.findOrganizerByPublicId(UUID.fromString(publicId))
                .orElseThrow(() -> new AgencyException(ORGANIZER_NOT_FOUND));
        validateUserToMakeChanges(organizer);

        userProfileRepository.findUserProfileByUsername(username)
                .orElseThrow(() -> new AgencyException(CANNOT_ASSIGN_TO_NOT_EXISTED_USER));

        organizer.assignNewUser(username);

        repository.save(organizer);
        log.info(OrganizerLogMessage.USER_ASSIGN_SUCCESSFULLY, publicId, username);
    }

    private void validateUserToMakeChanges(Organizer organizer) {
        UserProfile userInContext = contextUsers.getUserProfileFromAuthentication();

        if(RoleType.ADMIN != userInContext.getRole().getName() && !userInContext.getUsername().equals(organizer.getUsername())){
            throw new AgencyException(CANNOT_CHANGE_NOT_YOURS_ORGANIZER, organizer.getUsername());
        }
    }
}
