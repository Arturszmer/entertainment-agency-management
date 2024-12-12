package com.agency.dto.project;

import java.math.BigDecimal;

public record CostDto(
        String publicId,
        String costType,
        String costReference,
        String description,
        BigDecimal value
) {
}
