package com.agency.model;

import com.agency.dto.contractwork.BasicContractDetailsDto;
import com.agency.dto.contractwork.ContractWorkCreateDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ContractWorkModel {

    public final static LocalDate SIGN_DATE = LocalDate.of(2024, 1, 2);
    public final static LocalDate START_DATE = LocalDate.of(2024, 1, 2);
    public final static LocalDate END_DATE = LocalDate.of(2024, 1, 31);
    public final static String CONTRACTOR_PUBLIC_ID = "fb75951a-fe54-11ee-92c8-0242ac120003";
    public static final String PROJECT_NUMBER = "2024/STY/PRO11";
    public static final String PROJECT_PUBLIC_ID = "585dee47-e5d0-4485-b72e-7c2ceca6d886";


    public static ContractWorkCreateDto getContractWorkCreateDto(){
        BasicContractDetailsDto basicContractDetailsDto = new BasicContractDetailsDto(PROJECT_PUBLIC_ID, SIGN_DATE, START_DATE, END_DATE,
                "Subject of the contract", BigDecimal.valueOf(3000),
                "Additional information");
        return new ContractWorkCreateDto(PROJECT_NUMBER, CONTRACTOR_PUBLIC_ID, basicContractDetailsDto, true);
    }
}
