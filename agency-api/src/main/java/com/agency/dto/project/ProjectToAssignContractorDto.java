package com.agency.dto.project;

public record ProjectToAssignContractorDto(
        String publicId,
        String contractNumber,
        String organizerName,
        String projectSubject
) {
}
