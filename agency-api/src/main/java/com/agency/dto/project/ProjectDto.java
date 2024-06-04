package com.agency.dto.project;

import com.agency.dto.contractwork.BasicContractDetailsDto;
import com.agency.dto.contractwork.ContractShortDto;
import com.agency.dict.contract.ContractType;

import java.util.List;

public record ProjectDto(
        String contractNumber,
        BasicContractDetailsDto contractDetailsDto,
        ContractType contractType,
        List<ContractShortDto> contracts
) {
}
