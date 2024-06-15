package com.agency.dto.project;

import com.agency.dto.contractor.ContractorDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ProjectCreateDto(
        LocalDate signDate,
        LocalDate startDate,
        LocalDate endDate,
        String subjectOfTheContract,
        BigDecimal salary,
        String additionalInformation,
        String organizerPublicId,
        List<ContractorDto> contractorsDto
) {
}
