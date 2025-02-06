package com.agency.dto.project;

import com.agency.dict.project.ProjectStatus;

public record ProjectStatusUpdateRequest(
        String contractNumber,
        ProjectStatus status
) {
}
