package com.agency.documents.service;

import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.documentcontext.templatecontext.TemplateContext;
import com.agency.documents.assembler.DocumentTemplateAssembler;
import com.agency.documents.model.TemplateDocument;
import com.agency.documents.repository.TemplateDocumentRepository;
import com.agency.dto.document.TemplateDocumentDto;
import com.agency.exception.AgencyException;
import com.agency.exception.DocumentTemplateResult;
import com.agency.generator.service.FileWriterService;
import com.agency.service.DocumentTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class DocumentTemplateServiceImpl implements DocumentTemplateService {

    private final TemplateDocumentRepository templateDocumentRepository;
    private final DefaultDocumentTemplateResolver defaultDocumentTemplateResolver;
    private final FileWriterService fileWriter;
    private final String outputPath;

    public DocumentTemplateServiceImpl(TemplateDocumentRepository templateDocumentRepository,
                                       DefaultDocumentTemplateResolver defaultDocumentTemplateResolver,
                                       FileWriterService fileWriter,
                                       @Value("${doc-static-file-path}") String outputPath) {
        this.templateDocumentRepository = templateDocumentRepository;
        this.defaultDocumentTemplateResolver = defaultDocumentTemplateResolver;
        this.fileWriter = fileWriter;
        this.outputPath = outputPath;
    }

    @Override
    public TemplateDocumentDto saveDocumentTemplate(final MultipartFile file,
                                                    final String templateName,
                                                    boolean isDefault,
                                                    final TemplateContext templateContext) {

        checkIsDocumentTemplateByNameExists(templateName);

        log.info("Start uploading new document template with name: {} for the context: {}", templateName, templateContext);
        DocumentTemplateValidator.validate(file);

        String fileName = fileWriter.write(outputPath, DocContextType.TEMPLATE, file); // TODO: OBSŁUŻYĆ NULL

        isDefault = resolveIsDefaultAttribute(isDefault, templateContext);

        TemplateDocument templateDocument = new TemplateDocument(fileName, templateName, isDefault, templateContext);


        TemplateDocument saved = templateDocumentRepository.saveAndFlush(templateDocument);
        log.info("Saved new document template with name: {} and reference id {}, for context: {}",
                saved.getTemplateName(), saved.getReferenceId(), templateContext);
        return DocumentTemplateAssembler.toDto(saved);
    }

    @Override
    public void updateDocumentTemplate(MultipartFile file, String referenceId) {
        DocumentTemplateValidator.validate(file);

        templateDocumentRepository.findByReferenceId(UUID.fromString(referenceId)).ifPresent(templateDocument -> {
            String fileName = fileWriter.update(outputPath, DocContextType.TEMPLATE, file, templateDocument.getFileName());
            templateDocument.setFileName(fileName);
            templateDocumentRepository.save(templateDocument);
        });

    }

    private boolean resolveIsDefaultAttribute(boolean isDefault, TemplateContext templateContext) {
        if (isDefault) {
            defaultDocumentTemplateResolver.clearDefaultTemplate(templateContext);
        } else {
            isDefault = setTrueIfNoneExists(isDefault);
        }
        return isDefault;
    }

    private boolean setTrueIfNoneExists(boolean isDefault) {
        if (templateDocumentRepository.findAll().isEmpty()) {
            isDefault = true;
        }
        return isDefault;
    }

    private void checkIsDocumentTemplateByNameExists(String templateName) {
        Optional<TemplateDocument> existingTemplateOpt = templateDocumentRepository.findByTemplateName(templateName);
        if (existingTemplateOpt.isPresent()) {
            log.info("Template with name {} already exists.", templateName);
            throw new AgencyException(DocumentTemplateResult.TEMPLATE_NAME_FOR_DOC_CONTEXT_EXISTS, templateName, DocContextType.TEMPLATE.toString());
        }
    }
}
