package com.agency.contractmanagement.model;

import com.agency.contractmanagement.model.contractor.Contractor;
import com.agency.dto.AddressDto;
import com.agency.dto.contractor.ContractorCreateRequest;
import com.agency.user.assembler.UserProfileAssembler;

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

    public static Contractor contractorFromRequest(ContractorCreateRequest request){
        return new Contractor(request.name(), request.lastName(), request.pesel(), request.birthDate(),
                UserProfileAssembler.toEntity(request.addressDto()), request.phone(), request.contractorDescribe());
    }

    private static AddressDto createAddressDto() {
        return new AddressDto(
                "Warszawa", "Zwycięstwa", "00-001", "1", "1"
        );
    }
}
