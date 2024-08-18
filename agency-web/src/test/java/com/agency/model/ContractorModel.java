package com.agency.model;

import com.agency.dto.contractor.ContractorAssignDto;
import com.agency.dto.contractor.ContractorCreateRequest;
import com.agency.dto.project.ProjectContractorAssignDto;

import java.time.LocalDate;
import java.util.List;

import static com.agency.model.AddressModel.createDefaultAddressDto;

public class ContractorModel {

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String PESEL = "81021758606";
    public static final String PUBLIC_ID = "fb75951a-fe54-11ee-92c8-0242ac120003";
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

    public static ProjectContractorAssignDto assignProjectContractorRequestBuild(String projectNumber, List<ContractorAssignDto> contractors){
        return new ProjectContractorAssignDto(
                projectNumber, contractors);
    }

    public static ContractorAssignDto contractorAssignDtoRequestBuild(String publicId, String fullName, boolean alreadyAssigned){
        return new ContractorAssignDto(
                publicId, fullName, alreadyAssigned
        );
    }

}
