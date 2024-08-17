package com.agency.dto.project;

import com.agency.dto.contractor.ContractorShortInfoDto;

import java.util.List;

public record ProjectContractorAssignResponse(
        String publicId,
        List<ContractorShortInfoDto> contractors
) {
}
