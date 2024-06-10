package com.agency.service;

import com.agency.dto.organizer.OrganizerCreate;
import com.agency.dto.organizer.OrganizerDto;
import com.agency.dto.organizer.OrganizerNotesEdit;

public interface OrganizerService {

    OrganizerDto add(OrganizerCreate organizerDto);

    OrganizerDto edit(String publicId, OrganizerCreate organizerCreate);

    String editNotes(OrganizerNotesEdit notesEdit);

    void delete(String publicId);

    void assignUser(String publicId, String username);
}
