package com.agency.service;

import com.agency.dto.document.TemplateDocumentCreateRequest;
import com.agency.dto.document.TemplateDocumentDto;

public interface DocumentTemplateHtmlService extends Template {

    TemplateDocumentDto saveDocumentTemplate(TemplateDocumentCreateRequest request);
    TemplateDocumentDto updateDocumentTemplate(TemplateDocumentCreateRequest request);
    void removeDocumentTemplate(String templateName);
}
