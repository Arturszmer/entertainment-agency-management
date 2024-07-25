package com.agency.dto.contractor;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ContractorAssignDto(
        String publicId,
        String fullName,
        @JsonProperty(value = "alreadyAssigned")
        boolean alreadyAssigned
) {
}
