package com.agency.dto.contractwork;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContractWorkDto(
        String contractNumber,
        LocalDate signDate,
        LocalDate startDate,
        LocalDate endDate,
        String subjectOftheContract,
        BigDecimal salary,
        String additionalInformation,
        ContractType contractType,
        boolean withCopyrights
) {
}
