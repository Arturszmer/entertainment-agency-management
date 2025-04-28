package com.agency.service;

import com.agency.dto.document.TemplateDocumentCreateRequest;
import com.agency.dto.document.TemplateDocumentDto;
import com.agency.dto.document.TemplateDocumentUpdateRequest;

public interface DocumentTemplateHtmlService extends Template {

    TemplateDocumentDto saveDocumentTemplate(TemplateDocumentCreateRequest request);
    TemplateDocumentDto updateDocumentTemplate(TemplateDocumentUpdateRequest request);
    void removeDocumentTemplate(String templateName);
    String getDocumentTemplateDetails(String templateName);
}
