package com.agency.dto.project;

import java.math.BigDecimal;

public record CostDto(
        String costType,
        String costReference,
        String description,
        BigDecimal value
) {
}
