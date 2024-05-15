package com.agency.dto.organizer;

import com.agency.dto.AddressDto;

public record OrganizerCreate(
        String organizerName,
        String email,
        String telephone,
        AddressDto addressDto,
        String notes
) {
}
