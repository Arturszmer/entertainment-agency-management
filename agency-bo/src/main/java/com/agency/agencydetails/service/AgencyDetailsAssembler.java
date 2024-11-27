package com.agency.agencydetails.service;

import com.agency.agencydetails.model.AgencyDetails;
import com.agency.dto.agencydetails.AgencyDetailsDto;
import com.agency.user.assembler.AddressAssembler;

public class AgencyDetailsAssembler {

    public static AgencyDetailsDto toDto(AgencyDetails agencyDetails) {
        return new AgencyDetailsDto(
                agencyDetails.getAgencyName(),
                agencyDetails.getNip(),
                agencyDetails.getRegon(),
                agencyDetails.getPesel(),
                agencyDetails.getFirstName(),
                agencyDetails.getLastName(),
                agencyDetails.getKrsNumber(),
                AddressAssembler.toDto(agencyDetails.getAddress())
        );
    }
}
