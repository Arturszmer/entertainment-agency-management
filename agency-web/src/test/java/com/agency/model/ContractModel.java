package com.agency.model;

import com.agency.dto.contractwork.BasicContractDetailsDto;
import com.agency.dto.contractwork.ContractWorkCreateDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ContractModel {

    public static ContractWorkCreateDto createContractWorkCreateDto() {
        BasicContractDetailsDto basicContractDetailsDto = BasicContractDetailsDto.builder()
                .publicId("59b93937-1f1f-4b59-afa3-4cd50932036b")
                .contractSubject("Software development contract")
                .signDate(LocalDate.of(2024, 1, 10))
                .startDate(LocalDate.of(2024, 1, 10))
                .endDate(LocalDate.of(2024, 1, 11))
                .salary(BigDecimal.valueOf(5000.00))
                .additionalInformation("Additional contract information")
                .build();

        return ContractWorkCreateDto.builder()
                .projectContractNumber("2024/STY/PRO11")
                .contractorPublicId("fb75951a-fe54-11ee-92c8-0242ac120003")
                .contractDetailsDto(basicContractDetailsDto)
                .withCopyrights(true)
                .build();
    }
}
