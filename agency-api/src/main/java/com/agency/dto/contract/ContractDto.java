package com.agency.dto.contract;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContractDto(
        String contractNumber,
        LocalDate signDate,
        LocalDate startDate,
        LocalDate endDate,
        String subjectOftheContract,
        BigDecimal salary,
        String additionalInformation,
        ContractType contractType
) {
}
