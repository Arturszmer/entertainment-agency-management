package com.agency.dto.contractwork;

public record  ContractWorkSearchResultDto(
        ContractWorkDto contracts,
        String contractorName,
        String projectNumber
) {}
