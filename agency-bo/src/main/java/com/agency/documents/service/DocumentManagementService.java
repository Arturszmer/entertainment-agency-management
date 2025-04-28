package com.agency.documents.service;

import com.agency.documentcontext.doccontext.GenerationResult;
import com.agency.documents.model.ContractDocument;
import com.agency.documents.model.ContractHtmlDocument;
import com.agency.documents.model.TemplateDocument;
import com.agency.documents.repository.ContractDocumentRepository;
import com.agency.documents.repository.ContractHtmlDocumentRepository;
import com.agency.documents.repository.TemplateDocumentRepository;
import com.agency.exception.AgencyException;
import com.agency.exception.DocumentErrorResult;
import com.agency.exception.DocumentTemplateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentManagementService {

    private final ContractDocumentRepository contractDocumentRepository;
    private final ContractHtmlDocumentRepository contractHtmlDocumentRepository;
    private final TemplateDocumentRepository templateDocumentRepository;

    @Transactional
    public void saveGeneratedDocument(GenerationResult generationResult, UUID correlationId) {
        if(generationResult.isSuccess()) {
            saveContractDocument(generationResult.getFilename(), correlationId);
            saveContractHtmlContent(generationResult.getContent(), correlationId);
        } else {
            throw new AgencyException(DocumentErrorResult.DOCUMENT_NOT_GENERATED_SUCCESSFULLY);
        }
    }

    @Transactional
    public void saveErrorDocument(GenerationResult generationResult, UUID correlationId) {
        if(!generationResult.isSuccess()) {
            ContractDocument contractDocument = new ContractDocument(generationResult.getFilename(), correlationId, true);
            contractDocumentRepository.save(contractDocument);
            log.info("Saved contract document error file: {}", contractDocument);
        }
    }

    public TemplateDocument getTemplateDocument(String templateName) {
        return templateDocumentRepository.findByTemplateName(templateName)
                .orElseThrow(() -> new AgencyException(DocumentTemplateResult.DOCUMENT_TEMPLATE_NOT_FOUND));
    }

    private void saveContractDocument(String filename, UUID correlationId) {
        if(StringUtils.hasText(filename)) {
            ContractDocument contractDocument = new ContractDocument(filename, correlationId);
            contractDocumentRepository.save(contractDocument);
            log.info("Saved contract document: {}", contractDocument);
        }
    }

    private void saveContractHtmlContent(String content, UUID correlationId) {
        if(StringUtils.hasText(content)) {
            ContractHtmlDocument contractHtmlDocument = new ContractHtmlDocument(content, correlationId);
            contractHtmlDocumentRepository.save(contractHtmlDocument);
            log.info("Saved contract html document: {}", contractHtmlDocument);
        }
    }
}
