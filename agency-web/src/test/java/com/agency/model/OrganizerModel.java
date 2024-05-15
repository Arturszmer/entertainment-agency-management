package com.agency.model;

import com.agency.dto.organizer.OrganizerCreate;

import static com.agency.model.AddressModel.createDefaultAddressDto;

public class OrganizerModel {

    public static OrganizerCreate createDefaultOrganizer() {
        return new OrganizerCreate(
            "Organizer 1",
                "email@email.com",
                "tel",
                createDefaultAddressDto(),
                "Notes"
        );
    }
}
