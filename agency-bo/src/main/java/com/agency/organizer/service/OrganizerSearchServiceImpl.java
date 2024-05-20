package com.agency.organizer.service;

import com.agency.dto.organizer.OrganizerDto;
import com.agency.organizer.assembler.OrganizerAssembler;
import com.agency.organizer.repository.OrganizerRepository;
import com.agency.service.OrganizerSearchService;
import com.agency.user.helper.SecurityContextUsers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizerSearchServiceImpl implements OrganizerSearchService {

    private final OrganizerRepository repository;

    @Override
    public List<OrganizerDto> findAll() {
        return repository.findAll().stream()
                .map(OrganizerAssembler::toDto)
                .toList();
    }

    @Override
    public OrganizerDto findByPublicId(String publicId) {
        return OrganizerAssembler.toDto(repository.findOrganizerByPublicId(UUID.fromString(publicId))
                .orElseThrow(() -> new IllegalArgumentException("Organizer with public id " + publicId + " not found")));
    }

    @Override
    public List<OrganizerDto> findByUsername() {
        return repository.findAllByUsername(SecurityContextUsers.getUsernameFromAuthenticatedUser()).stream()
                .map(OrganizerAssembler::toDto)
                .toList();
    }

}
