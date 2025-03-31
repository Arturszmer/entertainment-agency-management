package com.agency.dto.document;

import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.documentcontext.templatecontext.TemplateContext;

public record TemplateDocumentCreateRequest(
        String htmlContent,
        String templateName,
        DocContextType docContextType,
        boolean isDefault,
        TemplateContext templateContext
) {
}
