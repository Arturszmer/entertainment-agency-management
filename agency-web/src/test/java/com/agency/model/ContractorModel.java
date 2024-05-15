package com.agency.model;

import com.agency.dto.contractor.ContractorCreateRequest;

import java.time.LocalDate;

import static com.agency.model.AddressModel.createDefaultAddressDto;

public class ContractorModel {

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String PESEL = "81021758606";
    public static final LocalDate BIRTH_DATE = LocalDate.of(1981, 2, 17);


    public static ContractorCreateRequest contractorCreateRequestBuild(){
        return new ContractorCreateRequest(
                FIRST_NAME, LAST_NAME, PESEL, BIRTH_DATE, createDefaultAddressDto(), "500123456", "email@email.com", "describe"
        );
    }

    public static ContractorCreateRequest contractorCreateCustomRequestBuild(String firstName, String lastName, String pesel){
        return new ContractorCreateRequest(
                firstName, lastName, pesel, BIRTH_DATE, createDefaultAddressDto(), "500123456", "email@email.com", "describe"
        );
    }

}
