package com.agency.agencydetails.model;

import com.agency.dto.agencydetails.AgencyDetailsDto;
import com.agency.user.model.Address;

import static com.agency.contractmanagement.model.ContractorModel.createAddressDto;

public class AgencyDetailsModel {

    public static AgencyDetailsDto getAgencyDetailsDto(){
        return new AgencyDetailsDto(
                "Agency",
                "1234567891",
                null,
                null,
                null,
                createAddressDto()
        );
    }

    public static AgencyDetails getAgencyDetails(){
        return new AgencyDetails(
                "Entertainment",
                "9234567891",
                null,
                null,
                null,
                createAddress()
        );
    }

    private static Address createAddress() {
        return new Address(
                "Warszawa",
                "Zwycięstwa",
                "mazowieckie",
                "00-001",
                "1",
                "1"
        );
    }
}
