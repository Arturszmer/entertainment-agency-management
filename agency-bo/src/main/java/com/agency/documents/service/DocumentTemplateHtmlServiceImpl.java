package com.agency.documents.service;

import com.agency.documentcontext.templatecontext.TemplateContext;
import com.agency.documents.assembler.DocumentTemplateAssembler;
import com.agency.documents.model.TemplateDocument;
import com.agency.documents.repository.TemplateDocumentRepository;
import com.agency.documents.utils.TemplateSanitizer;
import com.agency.dto.document.TemplateDocumentCreateRequest;
import com.agency.dto.document.TemplateDocumentDto;
import com.agency.service.DocumentTemplateHtmlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static org.springframework.util.Assert.notNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentTemplateHtmlServiceImpl implements DocumentTemplateHtmlService {

    private final TemplateDocumentRepository templateDocumentRepository;
    private final DefaultDocumentTemplateResolver defaultDocumentTemplateResolver;


    @Override
    public TemplateDocumentDto saveDocumentTemplate(TemplateDocumentCreateRequest request) {
        String templateName = request.templateName();
        TemplateContext templateContext = request.templateContext();

        log.info("Start saving new document template with name: {} for the context: {}", templateName, templateContext);
        DocumentTemplateValidator validator = new DocumentTemplateValidator(templateName, templateDocumentRepository);
        validator.validateTemplateNameMustBeUnique();

        String sanitizedTemplateContent = TemplateSanitizer.sanitize(request.htmlContent());
        notNull(sanitizedTemplateContent, "Html content cannot be null");
        boolean isDefaultResolved = defaultDocumentTemplateResolver.resolveIsDefaultAttribute(request.isDefault(), templateContext);

        TemplateDocument templateDocument = new TemplateDocument(templateName, isDefaultResolved,
                sanitizedTemplateContent, templateContext);

        TemplateDocument saved = templateDocumentRepository.save(templateDocument);
        log.info("Saved new document template with html content with name: {} for context: {}", templateName, templateContext);
        return DocumentTemplateAssembler.toDto(saved);
    }

    @Override
    public TemplateDocumentDto updateDocumentTemplate(TemplateDocumentCreateRequest request) {
        return null;
    }

    @Override
    public void removeDocumentTemplate(String templateName) {
        TemplateDocument templateDocument = templateDocumentRepository.findByTemplateName(templateName)
                .orElseThrow(() -> new IllegalArgumentException("Template not found"));

        templateDocumentRepository.delete(templateDocument);
        templateDocumentRepository.flush();
        defaultDocumentTemplateResolver.setLatestModifiedTemplateAsDefault(templateDocument.getTemplateContext());
        log.info("Removed template with name: {}", templateName);
    }

    @Override
    public void setAsDefault(String referenceId) {

    }
}
