package com.agency.dto.contractwork;

import java.time.LocalDate;

public record ContractShortDto(
        String publicId,
        String contractNumber,
        LocalDate startDate,
        LocalDate endDate,
        String filename
) {
}
