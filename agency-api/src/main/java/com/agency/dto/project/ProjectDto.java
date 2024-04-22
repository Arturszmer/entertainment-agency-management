package com.agency.dto.project;

import com.agency.dto.contractwork.ContractShortDto;
import com.agency.dto.contractwork.ContractType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ProjectDto(
        String contractNumber,
        LocalDate signDate,
        LocalDate startDate,
        LocalDate endDate,
        String subjectOfTheContract,
        BigDecimal salary,
        String additionalInformation,
        ContractType contractType,
        List<ContractShortDto> contracts
) {
}
