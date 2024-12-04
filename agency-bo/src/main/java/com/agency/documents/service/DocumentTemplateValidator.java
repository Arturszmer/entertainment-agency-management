package com.agency.documents.service;

import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.documents.repository.TemplateDocumentRepository;
import com.agency.exception.AgencyException;
import com.agency.exception.DocumentTemplateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
class DocumentTemplateValidator {

    private final String templateName;
    private final TemplateDocumentRepository templateDocumentRepository;

    static void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty");
        }

        String originalFilename = file.getOriginalFilename();
        if (!StringUtils.hasText(originalFilename)) {
            throw new IllegalArgumentException("Filename must not be null");
        }

        if (!originalFilename.endsWith(".docx")) {
            throw new IllegalArgumentException("Only DOCX files are supported");
        }
    }

    void validateFilenameMustBeUnique(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (templateDocumentRepository.existsTemplateDocumentByFileName(originalFilename)) {
            throw new AgencyException(DocumentTemplateResult.TEMPLATE_FILENAME_IS_NOT_UNIQUE, originalFilename);
        }
    }

    void validateFilenameMustBeEqual(MultipartFile file, String updatingFilename) {
        String originalFilename = file.getOriginalFilename();
        if (!updatingFilename.equals(originalFilename)) {
            throw new AgencyException(DocumentTemplateResult.TEMPLATE_FILENAME_MUST_BE_EQUAL, updatingFilename, originalFilename);
        }
    }

    void validateTemplateNameMustBeUnique() {
        if (templateDocumentRepository.existsTemplateDocumentByTemplateName(templateName)) {
            throw new AgencyException(DocumentTemplateResult.TEMPLATE_NAME_FOR_DOC_CONTEXT_EXISTS, templateName, DocContextType.TEMPLATE.toString());
        }
    }

}
