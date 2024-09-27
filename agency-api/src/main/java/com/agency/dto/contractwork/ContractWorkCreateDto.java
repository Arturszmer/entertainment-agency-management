package com.agency.dto.contractwork;

import lombok.Builder;

@Builder
public record ContractWorkCreateDto(
        String projectContractNumber,
        String contractorPublicId,
        BasicContractDetailsDto contractDetailsDto,
        boolean withCopyrights

) {
}
