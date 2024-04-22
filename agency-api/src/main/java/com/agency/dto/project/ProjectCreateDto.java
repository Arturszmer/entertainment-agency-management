package com.agency.dto.project;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProjectCreateDto(
        LocalDate signDate,
        LocalDate startDate,
        LocalDate endDate,
        String subjectOfTheContract,
        BigDecimal salary,
        String additionalInformation
) {
}
