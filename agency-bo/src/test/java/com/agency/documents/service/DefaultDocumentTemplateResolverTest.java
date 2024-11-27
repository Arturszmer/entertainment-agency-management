package com.agency.documents.service;

import com.agency.documentcontext.templatecontext.TemplateContext;
import com.agency.documents.model.TemplateDocument;
import com.agency.documents.model.TemplateDocumentBuilder;
import com.agency.documents.repository.TemplateDocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultDocumentTemplateResolverTest {

    private DefaultDocumentTemplateResolver resolver;
    private final TemplateDocumentRepository templateDocumentRepository = mock(TemplateDocumentRepository.class);

    @BeforeEach
    void setUp() {
        resolver = new DefaultDocumentTemplateResolver(templateDocumentRepository);
    }

    @Test
    void should_set_default_template() {
        //given
        TemplateContext templateContext = TemplateContext.CONTRACT_WORK;
        TemplateDocument defaultTemplate = TemplateDocumentBuilder.aTemplateDocument()
                .withTemplateName("default")
                .withDefault(true)
                .build();
        TemplateDocument notDefaultTemplate = TemplateDocumentBuilder.aTemplateDocument()
                .withTemplateName("notDefault")
                .withDefault(false)
                .build();

        when(templateDocumentRepository.findAllTemplateDocumentsByTemplateContext(templateContext)).thenReturn(List.of(defaultTemplate, notDefaultTemplate));

        //when
        resolver.clearDefaultTemplate(templateContext);

        //then
        assertFalse(defaultTemplate.isDefault());
    }


}
