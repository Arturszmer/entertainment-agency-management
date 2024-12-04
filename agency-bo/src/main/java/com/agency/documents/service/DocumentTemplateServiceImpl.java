package com.agency.documents.service;

import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.documentcontext.templatecontext.TemplateContext;
import com.agency.documents.assembler.DocumentTemplateAssembler;
import com.agency.documents.model.TemplateDocument;
import com.agency.documents.repository.TemplateDocumentRepository;
import com.agency.dto.document.TemplateDocumentDto;
import com.agency.exception.AgencyException;
import com.agency.exception.DocumentTemplateResult;
import com.agency.generator.service.FileRemoveService;
import com.agency.generator.service.FileWriterService;
import com.agency.service.DocumentTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static com.agency.documents.service.DocumentTemplateValidator.validateFile;

@Service
@Slf4j
public class DocumentTemplateServiceImpl implements DocumentTemplateService {

    private final TemplateDocumentRepository templateDocumentRepository;
    private final DefaultDocumentTemplateResolver defaultDocumentTemplateResolver;
    private final FileWriterService fileWriterService;
    private final FileRemoveService fileRemoveService;

    public DocumentTemplateServiceImpl(TemplateDocumentRepository templateDocumentRepository,
                                       DefaultDocumentTemplateResolver defaultDocumentTemplateResolver,
                                       FileWriterService fileWriterService,
                                       FileRemoveService fileRemoveService) {
        this.templateDocumentRepository = templateDocumentRepository;
        this.defaultDocumentTemplateResolver = defaultDocumentTemplateResolver;
        this.fileWriterService = fileWriterService;
        this.fileRemoveService = fileRemoveService;
    }

    @Override
    public TemplateDocumentDto saveDocumentTemplate(final MultipartFile file,
                                                    final String templateName,
                                                    boolean isDefault,
                                                    final TemplateContext templateContext) {

        validateFile(file);
        log.info("Start uploading new document template with name: {} for the context: {}", templateName, templateContext);
        DocumentTemplateValidator validator = new DocumentTemplateValidator(templateName, templateDocumentRepository);
        validator.validateFilenameMustBeUnique(file);
        validator.validateTemplateNameMustBeUnique();

        String filename = fileWriterService.write(DocContextType.TEMPLATE, file);

        boolean isDefaultResolved = resolveIsDefaultAttribute(isDefault, templateContext);

        TemplateDocument templateDocument = new TemplateDocument(filename, templateName, isDefaultResolved, templateContext);


        TemplateDocument saved = templateDocumentRepository.saveAndFlush(templateDocument);
        log.info("Saved new document template with name: {} and reference id {}, for context: {}",
                saved.getTemplateName(), saved.getReferenceId(), templateContext);
        return DocumentTemplateAssembler.toDto(saved);
    }

    @Override
    public void updateDocumentTemplate(MultipartFile file, String referenceId) {
        validateFile(file);
        log.info("Start updating document template with reference id: {}", referenceId);
        templateDocumentRepository.findByReferenceId(UUID.fromString(referenceId)).ifPresent(templateDocument -> {
            new DocumentTemplateValidator(templateDocument.getTemplateName(), templateDocumentRepository)
                    .validateFilenameMustBeEqual(file, templateDocument.getFileName());

            String fileName = fileWriterService.write(DocContextType.TEMPLATE, file);
            templateDocument.setFileName(fileName);
            templateDocumentRepository.saveAndFlush(templateDocument);
            log.info("Updated document template with reference id: {}", referenceId);
        });

    }

    @Override
    public void removeTemplateDocument(String referenceId) {
        TemplateDocument templateDocument = templateDocumentRepository.findByReferenceId(UUID.fromString(referenceId))
                .orElseThrow(() -> new AgencyException(DocumentTemplateResult.DOCUMENT_TEMPLATE_NOT_FOUND));

        fileRemoveService.removeFile(templateDocument.getFileName(), DocContextType.TEMPLATE.toString());
        templateDocumentRepository.delete(templateDocument);
        defaultDocumentTemplateResolver.setLatestModifiedTemplateAsDefault(templateDocument.getTemplateContext());
        log.info("Deleted template document from DB is finished: {}", templateDocument.getTemplateName());
    }

    private boolean resolveIsDefaultAttribute(boolean isDefault, TemplateContext templateContext) {
        defaultDocumentTemplateResolver.clearDefaultTemplate(templateContext);
        if(isDefault){
            return true;
        } else {
            return setTrueIfNoneExists(templateContext);
        }
    }

    private boolean setTrueIfNoneExists(TemplateContext templateContext) {
        return templateDocumentRepository.findAllTemplateDocumentsByTemplateContext(templateContext).isEmpty();
    }
}
