package com.agency.documents.assembler;

import com.agency.documents.model.TemplateDocument;
import com.agency.dto.document.TemplateDocumentDto;

public class DocumentTemplateAssembler {

    public static TemplateDocumentDto toDto(final TemplateDocument templateDocument) {
        return new TemplateDocumentDto(
                templateDocument.getFileName(),
                templateDocument.getContextType(),
                templateDocument.getReferenceId() != null
                        ? templateDocument.getReferenceId().toString()
                        : null,
                templateDocument.getHtmlContent(),
                templateDocument.getTemplateName(),
                templateDocument.isDefault(),
                templateDocument.getTemplateContext(),
                templateDocument.getModificationTimestamp()
        );
    }
}
