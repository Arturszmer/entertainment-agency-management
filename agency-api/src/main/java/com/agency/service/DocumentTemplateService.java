package com.agency.service;

import com.agency.documentcontext.templatecontext.TemplateContext;
import com.agency.dto.document.TemplateDocumentDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocumentTemplateService extends DocumentService {

    TemplateDocumentDto saveDocumentTemplate(MultipartFile file, String templateName, boolean isDefault, TemplateContext templateContext);
    void updateDocument(MultipartFile file, String referenceId, String templateName) throws IOException;
    void setAsDefault(String referenceId);

    void updateDocumentTemplateName(String referenceId, String templateName);
}
