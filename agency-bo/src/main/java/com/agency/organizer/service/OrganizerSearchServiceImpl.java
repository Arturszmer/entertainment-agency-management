package com.agency.organizer.service;

import com.agency.dto.organizer.OrganizerDto;
import com.agency.organizer.assembler.OrganizerAssembler;
import com.agency.organizer.repository.OrganizerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizerSearchServiceImpl implements com.agency.service.OrganizerSearchService {

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
}
