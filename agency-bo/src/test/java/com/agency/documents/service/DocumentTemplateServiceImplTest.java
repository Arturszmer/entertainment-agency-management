package com.agency.documents.service;

import com.agency.documents.repository.TemplateDocumentRepository;
import com.agency.generator.service.FileWriterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class DocumentTemplateServiceImplTest {

    private final TemplateDocumentRepository documentRepository = mock(TemplateDocumentRepository.class);
    private final DefaultDocumentTemplateResolver resolver = mock(DefaultDocumentTemplateResolver.class);
    private final FileWriterService fileWriterService = mock(FileWriterService.class);
    private final String OUTPUT_PATH = "path";

    private DocumentTemplateServiceImpl documentTemplateService;

    @BeforeEach
    void setUp() {
        documentTemplateService = new DocumentTemplateServiceImpl(documentRepository, resolver, fileWriterService, OUTPUT_PATH);
    }

    @Test
    public void should_save_template() {
        // given


        // when

        // then

    }


}
