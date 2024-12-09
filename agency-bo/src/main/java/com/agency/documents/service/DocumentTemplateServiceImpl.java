package com.agency.documents.service;

import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.documentcontext.templatecontext.TemplateContext;
import com.agency.documents.assembler.DocumentTemplateAssembler;
import com.agency.documents.model.TemplateDocument;
import com.agency.documents.repository.TemplateDocumentRepository;
import com.agency.dto.document.TemplateDocumentDto;
import com.agency.exception.AgencyException;
import com.agency.exception.DocumentTemplateResult;
import com.agency.generator.service.FileDownloadService;
import com.agency.generator.service.FileRemoveService;
import com.agency.generator.service.FileWriterService;
import com.agency.service.DocumentTemplateService;
import com.agency.user.helper.SecurityContextUsers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
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
    private final FileDownloadService fileDownloadService;

    public DocumentTemplateServiceImpl(TemplateDocumentRepository templateDocumentRepository,
                                       DefaultDocumentTemplateResolver defaultDocumentTemplateResolver,
                                       FileWriterService fileWriterService,
                                       FileRemoveService fileRemoveService, FileDownloadService fileDownloadService) {
        this.templateDocumentRepository = templateDocumentRepository;
        this.defaultDocumentTemplateResolver = defaultDocumentTemplateResolver;
        this.fileWriterService = fileWriterService;
        this.fileRemoveService = fileRemoveService;
        this.fileDownloadService = fileDownloadService;
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
    public void updateDocumentTemplate(MultipartFile file, String referenceId, String templateName) {
        validateFile(file);
        log.info("Start updating document template with reference id: {}", referenceId);
        templateDocumentRepository.findByReferenceId(UUID.fromString(referenceId)).ifPresent(templateDocument -> {
            new DocumentTemplateValidator(templateName, templateDocumentRepository)
                    .validateIsFilenameExistsInOtherTemplate(file.getOriginalFilename(), templateDocument.getReferenceId());
            String fileName = fileWriterService.update(DocContextType.TEMPLATE, file, templateDocument.getFileName());
            templateDocument.setFileName(fileName);
            templateDocument.setTemplateName(templateName);
            templateDocumentRepository.saveAndFlush(templateDocument);
            logUpdate(referenceId);
        });

    }

    @Override
    public void updateDocumentTemplateName(String referenceId, String templateName) {
        templateDocumentRepository.findByReferenceId(UUID.fromString(referenceId))
                .ifPresent(templateDocument -> {
                    new DocumentTemplateValidator(templateName, templateDocumentRepository).validateTemplateNameMustBeUnique();
                    templateDocument.setTemplateName(templateName);
                    templateDocumentRepository.save(templateDocument);
                    logUpdate(referenceId);
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

    @Override
    public Resource downloadDocumentTemplate(String filename) {
        String usernameFromAuthenticatedUser = SecurityContextUsers.getUsernameFromAuthenticatedUser();
        return fileDownloadService.downloadFile(filename, DocContextType.TEMPLATE, usernameFromAuthenticatedUser);
    }

    @Override
    public void setAsDefault(String referenceId) {
        templateDocumentRepository.findByReferenceId(UUID.fromString(referenceId)).ifPresent(templateDocument -> {
            defaultDocumentTemplateResolver.clearDefaultTemplate(templateDocument.getTemplateContext());
            templateDocument.setDefault(true);
            templateDocumentRepository.save(templateDocument);
            log.info("Set default document template with reference id: {}", referenceId);
        });
    }

    private boolean resolveIsDefaultAttribute(boolean isDefault, TemplateContext templateContext) {
        if(isDefault){
            defaultDocumentTemplateResolver.clearDefaultTemplate(templateContext);
            return true;
        } else {
            return setTrueIfNoneExists(templateContext);
        }
    }

    private boolean setTrueIfNoneExists(TemplateContext templateContext) {
        return templateDocumentRepository.findAllTemplateDocumentsByTemplateContext(templateContext).isEmpty();
    }

    private void logUpdate(String referenceId) {
        log.info("Update document template with reference id: {}", referenceId);
    }
}
