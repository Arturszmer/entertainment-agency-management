package com.agency.dto.project;

import java.math.BigDecimal;
import java.util.UUID;

public record CostDto(
        UUID publicId,
        String costType,
        String costReference,
        String description,
        String modifiedBy,
        BigDecimal value,
        boolean isGenerated
) {
}
