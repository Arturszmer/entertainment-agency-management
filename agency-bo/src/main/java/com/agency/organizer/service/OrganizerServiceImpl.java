package com.agency.organizer.service;

import com.agency.dto.organizer.OrganizerCreate;
import com.agency.dto.organizer.OrganizerDto;
import com.agency.organizer.model.Organizer;
import com.agency.organizer.repository.OrganizerRepository;
import com.agency.service.OrganizerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.agency.organizer.assembler.OrganizerAssembler.toDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizerServiceImpl implements OrganizerService {

    private final OrganizerRepository repository;

    @Override
    public OrganizerDto add(OrganizerCreate organizerDto) {
        Organizer organizer = repository.save(Organizer.createOrganizer(organizerDto));
        log.info("Organizer with publicId {} added successfully", organizer.getPublicId());

        return toDto(organizer);
    }
}
