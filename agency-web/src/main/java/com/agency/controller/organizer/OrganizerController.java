package com.agency.controller.organizer;

import com.agency.dto.organizer.OrganizerCreate;
import com.agency.dto.organizer.OrganizerDto;
import com.agency.dto.organizer.OrganizerNotesEdit;
import com.agency.service.OrganizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PutMapping("/{public-id}")
    public ResponseEntity<OrganizerDto> edit(@PathVariable("public-id") String publicId, @RequestBody OrganizerCreate organizerCreate){
        return ResponseEntity.ok(service.edit(publicId, organizerCreate));
    }

    @PatchMapping("/notes")
    public ResponseEntity<String> editNotes(@RequestBody OrganizerNotesEdit notesEdit){
        return ResponseEntity.ok(service.editNotes(notesEdit));
    }

    @DeleteMapping("/{public-id}")
    public ResponseEntity<Void> delete(@PathVariable("public-id") String publicId){
        service.delete(publicId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/user-assign/{public-id}")
    public ResponseEntity<Void> assignToUser(@PathVariable("public-id") String publicId, @RequestParam("username") String username){
        service.assignUser(publicId, username);
        return ResponseEntity.ok().build();
    }
}
