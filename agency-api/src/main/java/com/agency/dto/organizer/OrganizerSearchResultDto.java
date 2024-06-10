package com.agency.dto.organizer;

public record OrganizerSearchResultDto(
        String publicId,
        String organizerName,
        String voivodeship,
        String city,
        String address,
        String email,
        String telephone,
        String username,
        String notes
) {
}
