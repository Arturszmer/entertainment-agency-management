package com.agency.dto.contractwork;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record BasicContractDetailsDto(
        String publicId,
        LocalDate signDate,
        LocalDate startDate,
        LocalDate endDate,
        String contractSubject,
        BigDecimal salary,
        String additionalInformation
) {
}
