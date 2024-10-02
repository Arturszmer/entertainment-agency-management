package com.agency.dto.project;

import java.math.BigDecimal;

public record CostCreateDto(
        String projectNumber,
        String costType,
        String costReference,
        String costDescription,
        BigDecimal value
) {
}
