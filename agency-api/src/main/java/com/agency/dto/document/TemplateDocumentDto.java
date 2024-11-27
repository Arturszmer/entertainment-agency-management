package com.agency.dto.document;

import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.documentcontext.templatecontext.TemplateContext;

import java.time.Instant;

public record TemplateDocumentDto(
        String filename,
        DocContextType contextType,
        String referenceId,
        String templateName,
        boolean isDefault,
        TemplateContext templateContext,
        Instant updatedDate
) {
}
