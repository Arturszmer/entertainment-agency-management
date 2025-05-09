package com.agency.contractmanagement.contractwork.service;

import com.agency.agencydetails.model.AgencyDetails;
import com.agency.agencydetails.model.AgencyDetailsModel;
import com.agency.agencydetails.repository.AgencyDetailsRepository;
import com.agency.contractmanagement.contractwork.model.ContractWork;
import com.agency.contractmanagement.contractwork.model.ContractWorkBuilder;
import com.agency.contractmanagement.contractwork.repository.ContractWorkRepository;
import com.agency.contractor.model.ContractorBuilder;
import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.documentcontext.doccontext.GenerationResult;
import com.agency.documentcontext.templatecontext.TemplateContext;
import com.agency.documents.model.TemplateDocument;
import com.agency.documents.service.DocumentManagementService;
import com.agency.dto.contractwork.DocumentGenerateRequest;
import com.agency.generator.service.FileWriterService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContractGeneratorTest {

    private ContractWorkGenerator contractWorkGenerator;
    private final ContractWorkRepository contractWorkRepository = mock(ContractWorkRepository.class);
    private final AgencyDetailsRepository agencyDetailsRepository = mock(AgencyDetailsRepository.class);
    private final FileWriterService fileWriterService = mock(FileWriterService.class);
    private final DocumentManagementService documentManagementService = mock(DocumentManagementService.class);
    private final ArgumentCaptor<ContractWork> contractWorkArgumentCaptor = ArgumentCaptor.forClass(ContractWork.class);

    @BeforeEach
    void setUp() {
        String docStaticFilepath = "src/test/resources/templates/";
        contractWorkGenerator = new ContractWorkGenerator(
                contractWorkRepository,
                agencyDetailsRepository,
                fileWriterService,
                documentManagementService,
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

        DocumentGenerateRequest generateRequest = getGenerateRequest(contractWork, templateName);

        when(documentManagementService.getTemplateDocument(templateName)).thenReturn(getTemplateDocument(fileName, templateName));
        when(contractWorkRepository.findContractWorkByContractNumber(contractWork.getContractNumber())).thenReturn(Optional.of(contractWork));
        when(agencyDetailsRepository.findAll()).thenReturn(List.of(agencyDetails));

        // when
        GenerationResult generate = contractWorkGenerator.generate(generateRequest);

        // then
        assertTrue(generate.isSuccess());
        verify(contractWorkRepository, times(1)).save(contractWorkArgumentCaptor.capture());
        ContractWork actualContractWork = contractWorkArgumentCaptor.getValue();
        assertEquals("John_Doe_", actualContractWork.getFilename().substring(0, 9));
        assertEquals(".docx", actualContractWork.getFilename().substring(actualContractWork.getFilename().length() - 5));

        verify(documentManagementService, times(1)).saveGeneratedDocument(any(), any());
    }

    @Test
    public void shouldGenerateErrorLog() {
        // given
        String publicID = "608e8a41-4513-47ae-ac53-539b5d8244ea";
        String templateName = "Template";
        String fileName = "Umowa o dzieło - template - failed.docx";

        ContractWork contractWork = ContractWorkBuilder.aContractWorkBuilder().withPublicId(publicID).withBasicData().withContractor(ContractorBuilder.aContractorBuilder().withBasicData().build()).build();

        AgencyDetails agencyDetails = AgencyDetailsModel.getAgencyDetails();

        DocumentGenerateRequest generateRequest = getGenerateRequest(contractWork, templateName);

        when(documentManagementService.getTemplateDocument(templateName)).thenReturn(getTemplateDocument(fileName, templateName));
        when(contractWorkRepository.findContractWorkByContractNumber(contractWork.getContractNumber())).thenReturn(Optional.of(contractWork));
        when(agencyDetailsRepository.findAll()).thenReturn(List.of(agencyDetails));

        // when
        GenerationResult generate = contractWorkGenerator.generate(generateRequest);

        // then
        assertFalse(generate.isSuccess());
        verify(contractWorkRepository, never()).save(any());

        verify(documentManagementService, times(1)).saveErrorDocument(any(), any());
    }

    @NotNull
    private static DocumentGenerateRequest getGenerateRequest(ContractWork contractWork, String templateName) {
        return new DocumentGenerateRequest(contractWork.getContractNumber(), templateName, DocContextType.CONTRACT_WORK);
    }

    private TemplateDocument getTemplateDocument(String fileName, String templateName) {
        return new TemplateDocument(
                fileName, templateName, true, TemplateContext.CONTRACT_WORK
        );
    }

}
