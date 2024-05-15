package com.agency.organizer.assembler;

import com.agency.dto.organizer.OrganizerDto;
import com.agency.organizer.model.Organizer;
import com.agency.user.assembler.AddressAssembler;

public class OrganizerAssembler {

    public static OrganizerDto toDto(Organizer organizer) {
        return new OrganizerDto(
                organizer.getPublicId().toString(),
                organizer.getOrganizerName(),
                organizer.getEmail(),
                organizer.getTelephone(),
                organizer.getUsername(),
                AddressAssembler.toDto(organizer.getAddress()),
                organizer.getNotes()
        );
    }
}
