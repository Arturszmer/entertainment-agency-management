package com.agency.agencydetails.model;

import com.agency.dto.agencydetails.AgencyDetailsDto;
import com.agency.user.model.Address;

import static com.agency.contractor.model.ContractorModel.createAddressDto;

public class AgencyDetailsModel {

    public static AgencyDetailsDto getAgencyDetailsDto(){
        return new AgencyDetailsDto(
                "Agency",
                "1234567891",
                null,
                null,
                "FirstName",
                "LastName",
                null,
                createAddressDto()
        );
    }

    public static AgencyDetails getAgencyDetails(){
        return new AgencyDetails(
                "Entertainment",
                "9234567891",
                null,
                "89120995302",
                "Andrzej",
                "Kowalski",
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
