package com.agency.dto.project;

import java.time.LocalDate;

public record ProjectSearchDto(
        String publicId,
        String contractNumber,
        String organizerName,
        String projectSubject,
        LocalDate startDate,
        LocalDate endDate,
        int contractorsNumber
) {
}
