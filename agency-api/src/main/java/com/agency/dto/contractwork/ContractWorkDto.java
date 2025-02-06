package com.agency.dto.contractwork;

import com.agency.dict.contract.ContractType;
import com.agency.dict.contract.ContractWorkStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContractWorkDto(
        String publicId,
        String contractNumber,
        LocalDate signDate,
        LocalDate startDate,
        LocalDate endDate,
        String contractSubject,
        BigDecimal salary,
        String additionalInformation,
        ContractType contractType,
        ContractWorkStatus status,
        boolean withCopyrights
) {
}
