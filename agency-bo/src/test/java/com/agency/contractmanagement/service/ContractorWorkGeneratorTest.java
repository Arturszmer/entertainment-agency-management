package com.agency.contractmanagement.service;

import com.agency.agencydetails.model.AgencyDetails;
import com.agency.agencydetails.model.AgencyDetailsModel;
import com.agency.agencydetails.repository.AgencyDetailsRepository;
import com.agency.contractmanagement.model.ContractWork;
import com.agency.contractmanagement.model.ContractWorkBuilder;
import com.agency.contractmanagement.repository.ContractWorkRepository;
import com.agency.contractor.model.ContractorBuilder;
import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.documentcontext.doccontext.GenerationResult;
import com.agency.documentcontext.templatecontext.TemplateContext;
import com.agency.documents.model.ContractDocument;
import com.agency.documents.model.TemplateDocument;
import com.agency.documents.repository.ContractDocumentRepository;
import com.agency.documents.repository.TemplateDocumentRepository;
import com.agency.generator.service.FileWriterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContractorWorkGeneratorTest {

    private ContractorWorkGenerator contractorWorkGenerator;
    private final ContractWorkRepository contractWorkRepository = mock(ContractWorkRepository.class);
    private final AgencyDetailsRepository agencyDetailsRepository = mock(AgencyDetailsRepository.class);
    private final ContractDocumentRepository contractDocumentRepository = mock(ContractDocumentRepository.class);
    private final TemplateDocumentRepository templateDocumentRepository = mock(TemplateDocumentRepository.class);
    private final FileWriterService fileWriterService = mock(FileWriterService.class);
    private final ArgumentCaptor<ContractWork> contractWorkArgumentCaptor = ArgumentCaptor.forClass(ContractWork.class);
    private final ArgumentCaptor<ContractDocument> contractDocumentArgumentCaptor = ArgumentCaptor.forClass(ContractDocument.class);

    @BeforeEach
    void setUp() {
        String docStaticFilepath = "src/test/resources/templates/";
        contractorWorkGenerator = new ContractorWorkGenerator(
                contractWorkRepository,
                agencyDetailsRepository,
                contractDocumentRepository,
                templateDocumentRepository,
                fileWriterService,
                docStaticFilepath
        );
    }

    @Test
    public void shouldGenerateWithSuccess() {
        // given
        String publicID = "608e8a41-4513-47ae-ac53-539b5d8244ea";
        String templateName = "Template";
        String fileName = "Umowa o dzieło - template.docx";

        ContractWork contractWork = ContractWorkBuilder.aContractWorkBuilder()
                .withPublicId(publicID)
                .withBasicData()
                .withContractor(ContractorBuilder.aContractorBuilder()
                        .withBasicData()
                        .build())
                .build();

        AgencyDetails agencyDetails = AgencyDetailsModel.getAgencyDetails();

        when(templateDocumentRepository.findByTemplateName(templateName)).thenReturn(Optional.of(getTemplateDocument(fileName, templateName)));
        when(contractWorkRepository.findContractWorkByPublicId(UUID.fromString(publicID))).thenReturn(Optional.of(contractWork));
        when(agencyDetailsRepository.findAll()).thenReturn(List.of(agencyDetails));

        // when
        GenerationResult generate = contractorWorkGenerator.generate(publicID, templateName, DocContextType.CONTRACT_WORK);

        // then
        assertTrue(generate.isSuccess());
        verify(contractWorkRepository, times(1)).save(contractWorkArgumentCaptor.capture());
        ContractWork actualContractWork = contractWorkArgumentCaptor.getValue();
        assertEquals("John_Doe_", actualContractWork.getFilename().substring(0, 9));
        assertEquals(".docx", actualContractWork.getFilename().substring(actualContractWork.getFilename().length() - 5));

        verify(contractDocumentRepository, times(1)).save(contractDocumentArgumentCaptor.capture());
        ContractDocument actualContractDocument = contractDocumentArgumentCaptor.getValue();
        assertEquals(actualContractWork.getFilename(), actualContractDocument.getFileName());
        assertNotNull(actualContractDocument.getReferenceId());
        assertFalse(actualContractDocument.isErrorFile());

    }

    @Test
    public void shouldGenerateErrorLog() {
        // given
        String publicID = "608e8a41-4513-47ae-ac53-539b5d8244ea";
        String templateName = "Template";
        String fileName = "Umowa o dzieło - template - failed.docx";

        ContractWork contractWork = ContractWorkBuilder.aContractWorkBuilder().withPublicId(publicID).withBasicData().withContractor(ContractorBuilder.aContractorBuilder().withBasicData().build()).build();

        AgencyDetails agencyDetails = AgencyDetailsModel.getAgencyDetails();

        when(templateDocumentRepository.findByTemplateName(templateName)).thenReturn(Optional.of(getTemplateDocument(fileName, templateName)));
        when(contractWorkRepository.findContractWorkByPublicId(UUID.fromString(publicID))).thenReturn(Optional.of(contractWork));
        when(agencyDetailsRepository.findAll()).thenReturn(List.of(agencyDetails));

        // when
        GenerationResult generate = contractorWorkGenerator.generate(publicID, templateName, DocContextType.CONTRACT_WORK);

        // then
        assertFalse(generate.isSuccess());
        verify(contractWorkRepository, never()).save(any());

        verify(contractDocumentRepository, times(1)).save(contractDocumentArgumentCaptor.capture());
        ContractDocument actualContractDocument = contractDocumentArgumentCaptor.getValue();
        assertEquals(".txt", actualContractDocument.getFileName().substring(actualContractDocument.getFileName().length() - 4));
        assertTrue(actualContractDocument.getFileName().contains("error"));
        assertNotNull(actualContractDocument.getReferenceId());
        assertTrue(actualContractDocument.isErrorFile());
    }

    private TemplateDocument getTemplateDocument(String fileName, String templateName) {
        return new TemplateDocument(
                fileName, templateName, true, TemplateContext.CONTRACT_WORK
        );
    }

}
