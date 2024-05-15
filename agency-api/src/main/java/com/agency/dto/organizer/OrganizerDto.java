package com.agency.dto.organizer;

import com.agency.dto.AddressDto;

public record OrganizerDto(
        String publicId,
        String organizerName,
        String email,
        String telephone,
        String username,
        AddressDto addressDto,
        String notes
) {
}
