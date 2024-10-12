package com.agency.dto.contractwork;

import java.time.LocalDate;

public record ContractWorkSearchRequest(
        String contractNumber,
        LocalDate signDate,
        LocalDate startDate,
        LocalDate endDate,
        String contractorFirstName,
        String contractorLastName,
        String contractorPesel,
        String projectNumber,
        int page,
        int size,
        String sort,
        String order
) {}
