package com.agency.documents.service;

import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.documentcontext.templatecontext.TemplateContext;
import com.agency.documents.model.TemplateDocument;
import com.agency.documents.model.TemplateDocumentBuilder;
import com.agency.documents.repository.TemplateDocumentRepository;
import com.agency.dto.document.TemplateDocumentDto;
import com.agency.generator.service.FileDownloadService;
import com.agency.generator.service.FileRemoveService;
import com.agency.generator.service.FileWriterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentTemplateServiceFileImplTest {

    private DocumentTemplateFileServiceImpl documentTemplateFileService;

    @Mock
    private TemplateDocumentRepository documentRepository;
    @Mock
    private DefaultDocumentTemplateResolver resolver;
    @Mock
    private FileWriterService fileWriterService;
    @Mock
    private FileRemoveService fileRemoveService;
    @Mock
    private FileDownloadService fileDownloadService;


    @BeforeEach
    void setUp() {
        documentTemplateFileService = new DocumentTemplateFileServiceImpl(documentRepository, resolver, fileWriterService, fileRemoveService, fileDownloadService);
    }

    @Test
    public void should_save_template() {
        // given
        String filename = "Umowa o dzieło - template.docx";
        MultipartFile multipartFile = mock(MultipartFile.class);
        String templateName = "Template one";
        boolean isDefault = true;
        TemplateContext templateContext = TemplateContext.CONTRACT_WORK;
        TemplateDocument templateDocument = TemplateDocumentBuilder.aTemplateDocument()
                .withTemplateName(templateName)
                .withDefault(isDefault)
                .withTemplateContext(templateContext)
                .withReferenceId(UUID.randomUUID())
                .withFilename(filename)
                .build();

        when(documentRepository.existsTemplateDocumentByTemplateName(templateName)).thenReturn(false);
        when(documentRepository.existsTemplateDocumentByFileName(anyString())).thenReturn(false);
        when(documentRepository.save(any())).thenReturn(templateDocument);
        when(fileWriterService.write(DocContextType.TEMPLATE, multipartFile)).thenReturn(filename);
        when(multipartFile.getOriginalFilename()).thenReturn(filename);

        // when
        TemplateDocumentDto templateDocumentDto = documentTemplateFileService.saveDocumentTemplate(multipartFile, templateName, true, TemplateContext.CONTRACT_WORK);
        // then
        assertNotNull(templateDocumentDto);
        assertTrue(templateDocumentDto.isDefault());
        assertEquals(TemplateContext.CONTRACT_WORK, templateDocument.getTemplateContext());
        assertEquals(filename, templateDocumentDto.filename());

    }

    @Test
    public void should_update_template() {
        // given
        String filename = "Umowa o dzieło - template.docx";
        MultipartFile multipartFile = mock(MultipartFile.class);
        String templateName = "Template one";
        boolean isDefault = true;
        String referenceId = "921b2a6e-6db4-4255-bd84-b98f1b58c03e";
        TemplateContext templateContext = TemplateContext.CONTRACT_WORK;
        TemplateDocument templateDocument = TemplateDocumentBuilder.aTemplateDocument()
                .withTemplateName(templateName)
                .withDefault(isDefault)
                .withTemplateContext(templateContext)
                .withReferenceId(UUID.fromString(referenceId))
                .withFilename(filename)
                .build();

        when(documentRepository.findByReferenceId(UUID.fromString(referenceId))).thenReturn(Optional.of(templateDocument));
        when(documentRepository.existsTemplateDocumentByFileNameAndReferenceIdIsNot(anyString(), any())).thenReturn(false);
        when(documentRepository.saveAndFlush(any())).thenReturn(templateDocument);
        when(fileWriterService.update(DocContextType.TEMPLATE, multipartFile, filename)).thenReturn(filename);
        when(multipartFile.getOriginalFilename()).thenReturn(filename);

        // when
        documentTemplateFileService.updateDocument(multipartFile, referenceId, templateName);

        // then
        ArgumentCaptor<TemplateDocument> captor = ArgumentCaptor.forClass(TemplateDocument.class);

        verify(documentRepository).saveAndFlush(captor.capture());
        assertEquals(TemplateContext.CONTRACT_WORK, captor.getValue().getTemplateContext());
        assertEquals(templateName, captor.getValue().getTemplateName());

    }

    @Test
    public void should_remove_template() {
        // given
        String filename = "Umowa o dzieło - template.docx";
        String templateName = "Template one";
        boolean isDefault = true;
        String referenceId = "921b2a6e-6db4-4255-bd84-b98f1b58c03e";
        TemplateContext templateContext = TemplateContext.CONTRACT_WORK;
        TemplateDocument templateDocument = TemplateDocumentBuilder.aTemplateDocument()
                .withTemplateName(templateName)
                .withDefault(isDefault)
                .withTemplateContext(templateContext)
                .withReferenceId(UUID.fromString(referenceId))
                .withFilename(filename)
                .build();

        when(documentRepository.findByReferenceId(UUID.fromString(referenceId))).thenReturn(Optional.of(templateDocument));

        // when
        documentTemplateFileService.removeDocument(referenceId);

        // then
        verify(fileRemoveService, times(1)).removeFile(filename, DocContextType.TEMPLATE.toString());
        verify(documentRepository, times(1)).delete(templateDocument);
        verify(resolver, times(1)).setLatestModifiedTemplateAsDefault(templateContext);

    }
}
