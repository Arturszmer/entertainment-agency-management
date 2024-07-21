package com.agency.dto.contractwork;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BasicContractDetailsDto(
        LocalDate signDate,
        LocalDate startDate,
        LocalDate endDate,
        String projectSubject,
        BigDecimal salary,
        String additionalInformation
) {
}
