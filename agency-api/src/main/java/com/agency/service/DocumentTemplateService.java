package com.agency.service;

import com.agency.documentcontext.templatecontext.TemplateContext;
import com.agency.dto.document.TemplateDocumentDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocumentTemplateService {

    TemplateDocumentDto saveDocumentTemplate(MultipartFile file, String templateName, boolean isDefault, TemplateContext templateContext);
    void updateDocumentTemplate(MultipartFile file, String referenceId) throws IOException;
}
