package com.agency.dto.document;

import java.util.UUID;

public record TemplateDocumentUpdateRequest(
        String htmlContent,
        String templateName,
        boolean isDefault,
        UUID referenceId
) {
}
