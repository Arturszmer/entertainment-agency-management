package com.agency.dto.contractwork;

public record ContractWorkCreateDto(
        String projectContractNumber,
        String contractorPublicId,
        BasicContractDetailsDto contractDetailsDto,
        boolean withCopyrights

) {
}
