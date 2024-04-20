package com.agency.dto.contract;

import java.time.LocalDate;

public record ContractShortDto(
        String contractNumber,
        LocalDate startDate,
        LocalDate endDate
) {
}
