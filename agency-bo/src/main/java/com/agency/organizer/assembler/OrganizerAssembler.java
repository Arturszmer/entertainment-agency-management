package com.agency.organizer.assembler;

import com.agency.dto.organizer.OrganizerDto;
import com.agency.dto.organizer.OrganizerSearchResultDto;
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

    public static OrganizerSearchResultDto mapToSearchResult(Organizer organizer){
        String city = organizer.getAddress().getCity();
        String voivodeship = organizer.getAddress().getVoivodeship();
        String address = organizer.getAddress().getStreetWithNumber();
        return new OrganizerSearchResultDto(
                organizer.getPublicId().toString(),
                organizer.getOrganizerName(),
                voivodeship,
                city,
                address,
                organizer.getEmail(),
                organizer.getTelephone(),
                organizer.getUsername(),
                organizer.getNotes()
        );
    }
}
