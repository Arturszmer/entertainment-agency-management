package com.agency.dto.project;

import com.agency.dict.contract.ContractType;
import com.agency.dict.project.ProjectStatus;
import com.agency.dto.contractwork.BasicContractDetailsDto;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ProjectDto(
        String contractNumber,
        BasicContractDetailsDto contractDetailsDto,
        ProjectStatus status,
        ContractType contractType,
        @JsonProperty(value = "isInternal")
        boolean isInternal,
        String organizerName
) {
}
