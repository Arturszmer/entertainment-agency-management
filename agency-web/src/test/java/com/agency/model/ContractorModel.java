package com.agency.model;

import com.agency.dto.AddressDto;
import com.agency.dto.contractor.ContractorCreateRequest;

import java.time.LocalDate;

public class ContractorModel {

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String PESEL = "81021758606";
    public static final LocalDate BIRTH_DATE = LocalDate.of(1981, 2, 17);


    public static ContractorCreateRequest contractorCreateRequestBuild(){
        return new ContractorCreateRequest(
                FIRST_NAME, LAST_NAME, PESEL, BIRTH_DATE, createAddressDto(), "500123456", "describe"
        );
    }

    public static ContractorCreateRequest contractorCreateCustomRequestBuild(String firstName, String lastName, String pesel){
        return new ContractorCreateRequest(
                firstName, lastName, pesel, BIRTH_DATE, createAddressDto(), "500123456", "describe"
        );
    }

    private static AddressDto createAddressDto() {
        return new AddressDto(
                "Warszawa", "Zwycięstwa", "00-001", "1", "1"
        );
    }
}
