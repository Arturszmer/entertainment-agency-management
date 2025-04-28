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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentManagementServiceTest {

    private DocumentManagementService documentManagementService;

    @Mock
    private ContractDocumentRepository contractDocumentRepository;
    @Mock
    private ContractHtmlDocumentRepository contractHtmlDocumentRepository;
    @Mock
    private TemplateDocumentRepository templateDocumentRepository;

    @BeforeEach
    void setUp() {
        documentManagementService = new DocumentManagementService(contractDocumentRepository,
                contractHtmlDocumentRepository, templateDocumentRepository);
    }

    @Test
    void should_save_generated_document_with_successful_result() {
        // Given
        UUID correlationId = UUID.randomUUID();
        GenerationResult result = GenerationResult.builder()
                .filename("test-document.pdf")
                .content("<html><body>Test Content</body></html>")
                .success(true)
                .build();

        // When
        documentManagementService.saveGeneratedDocument(result, correlationId);

        // Then
        ArgumentCaptor<ContractDocument> documentCaptor = ArgumentCaptor.forClass(ContractDocument.class);
        verify(contractDocumentRepository).save(documentCaptor.capture());
        ContractDocument savedDocument = documentCaptor.getValue();
        assertEquals("test-document.pdf", savedDocument.getFileName());
        assertEquals(correlationId, savedDocument.getContractPublicId());
        assertFalse(savedDocument.isErrorFile());

        ArgumentCaptor<ContractHtmlDocument> htmlCaptor = ArgumentCaptor.forClass(ContractHtmlDocument.class);
        verify(contractHtmlDocumentRepository).save(htmlCaptor.capture());
        ContractHtmlDocument savedHtml = htmlCaptor.getValue();
        assertEquals("<html><body>Test Content</body></html>", savedHtml.getContent());
        assertEquals(correlationId, savedHtml.getContractPublicId());
    }

    @Test
    void should_throw_exception_when_generation_result_not_successful() {
        // Given
        UUID correlationId = UUID.randomUUID();
        GenerationResult result = GenerationResult.builder()
                .filename("error-document.pdf")
                .success(false)
                .build();

        // When / Then
        AgencyException exception = assertThrows(AgencyException.class, () ->
                documentManagementService.saveGeneratedDocument(result, correlationId));
        assertEquals(DocumentErrorResult.DOCUMENT_NOT_GENERATED_SUCCESSFULLY.getCode(), exception.getErrorCode());

        // Verify no interactions with repositories
        verifyNoInteractions(contractDocumentRepository, contractHtmlDocumentRepository);
    }

    @Test
    void should_save_error_document_when_generation_failed() {
        // Given
        UUID correlationId = UUID.randomUUID();
        GenerationResult result = GenerationResult.builder()
                .filename("error-document.pdf")
                .success(false)
                .build();

        // When
        documentManagementService.saveErrorDocument(result, correlationId);

        // Then
        ArgumentCaptor<ContractDocument> documentCaptor = ArgumentCaptor.forClass(ContractDocument.class);
        verify(contractDocumentRepository).save(documentCaptor.capture());
        ContractDocument savedDocument = documentCaptor.getValue();
        assertEquals("error-document.pdf", savedDocument.getFileName());
        assertEquals(correlationId, savedDocument.getContractPublicId());
        assertTrue(savedDocument.isErrorFile());
    }

    @Test
    void should_not_save_error_document_when_generation_successful() {
        // Given
        UUID correlationId = UUID.randomUUID();
        GenerationResult result = GenerationResult.builder()
                .filename("test-document.pdf")
                .success(true)
                .build();

        // When
        documentManagementService.saveErrorDocument(result, correlationId);

        // Then
        verifyNoInteractions(contractDocumentRepository);
    }

    @Test
    void should_get_template_document_when_exists() {
        // Given
        String templateName = "invoice-template";
        TemplateDocument expectedTemplate = new TemplateDocument();
        when(templateDocumentRepository.findByTemplateName(templateName))
                .thenReturn(Optional.of(expectedTemplate));

        // When
        TemplateDocument result = documentManagementService.getTemplateDocument(templateName);

        // Then
        assertEquals(expectedTemplate, result);
        verify(templateDocumentRepository).findByTemplateName(templateName);
    }

    @Test
    void should_throw_exception_when_template_document_not_found() {
        // Given
        String templateName = "non-existent-template";
        when(templateDocumentRepository.findByTemplateName(templateName))
                .thenReturn(Optional.empty());

        // When / Then
        AgencyException exception = assertThrows(AgencyException.class, () ->
                documentManagementService.getTemplateDocument(templateName));
        assertEquals(DocumentTemplateResult.DOCUMENT_TEMPLATE_NOT_FOUND.getCode(), exception.getErrorCode());
    }

    @Test
    void should_not_save_contract_document_when_filename_is_empty() {
        // Given
        UUID correlationId = UUID.randomUUID();
        GenerationResult result = GenerationResult.builder()
                .filename("")
                .content("<html><body>Test Content</body></html>")
                .success(true)
                .build();

        // When
        documentManagementService.saveGeneratedDocument(result, correlationId);

        // Then
        verify(contractDocumentRepository, never()).save(any());

        // HTML document should still be saved
        verify(contractHtmlDocumentRepository).save(any(ContractHtmlDocument.class));
    }

    @Test
    void should_not_save_contract_html_document_when_content_is_empty() {
        // Given
        UUID correlationId = UUID.randomUUID();
        GenerationResult result = GenerationResult.builder()
                .filename("test-document.pdf")
                .content("")
                .success(true)
                .build();

        // When
        documentManagementService.saveGeneratedDocument(result, correlationId);

        // Then
        verify(contractHtmlDocumentRepository, never()).save(any());

        // Contract document should still be saved
        verify(contractDocumentRepository).save(any(ContractDocument.class));
    }

    @Test
    void should_handle_null_values_correctly() {
        // Given
        UUID correlationId = UUID.randomUUID();
        GenerationResult result = GenerationResult.builder()
                .filename(null)
                .content(null)
                .success(true)
                .build();

        // When
        documentManagementService.saveGeneratedDocument(result, correlationId);

        // Then
        verify(contractDocumentRepository, never()).save(any());
        verify(contractHtmlDocumentRepository, never()).save(any());
    }
}

