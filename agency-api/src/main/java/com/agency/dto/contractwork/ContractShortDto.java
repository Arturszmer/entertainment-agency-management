package com.agency.dto.contractwork;

import java.time.LocalDate;

public record ContractShortDto(
        String contractNumber,
        LocalDate startDate,
        LocalDate endDate
) {
}
