package com.agency.controller.organizer;

import com.agency.dto.organizer.OrganizerCreate;
import com.agency.dto.organizer.OrganizerDto;
import com.agency.service.OrganizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAuthority('ORGANIZER_MANAGEMENT')")
@RequestMapping("api/v1/organizer")
@RequiredArgsConstructor
public class OrganizerController {

    private final OrganizerService service;

    @PostMapping
    public ResponseEntity<OrganizerDto> add(@RequestBody OrganizerCreate organizerCreate) {
        return ResponseEntity.ok(service.add(organizerCreate));
    }
}
