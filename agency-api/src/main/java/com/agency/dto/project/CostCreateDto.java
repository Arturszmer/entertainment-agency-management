package com.agency.dto.project;

import java.math.BigDecimal;
import java.util.UUID;

public record CostCreateDto(
        UUID projectPublicId,
        String costType,
        String costReference,
        String description,
        BigDecimal value
) {
}
