package com.agency.dto.project;

import com.agency.dto.contractor.ContractorAssignDto;

import java.util.List;

public record ProjectContractorAssignDto(
        String projectNumber,
        List<ContractorAssignDto> contractors
) {
}
