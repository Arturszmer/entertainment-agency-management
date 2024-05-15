package com.agency.controller.organizer;

import com.agency.dto.organizer.OrganizerDto;
import com.agency.service.OrganizerSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('ORGANIZER_MANAGEMENT')")
@RequestMapping("api/v1/organizer")
@RequiredArgsConstructor
public class OrganizerSearchController {

    private final OrganizerSearchService service;

    @GetMapping
    public ResponseEntity<List<OrganizerDto>> getAllOrganizers() {
        return ResponseEntity.ok(service.findAll());
    }
}
