package com.agency.dto.contractwork;

public record  ContractWorkSearchResultDto(
        ContractWorkDto contract,
        String firstName,
        String lastName,
        String projectNumber
) {}
