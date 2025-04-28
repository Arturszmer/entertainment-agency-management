package com.agency.contractmanagement.contractwork.service;

import com.agency.agencydetails.model.AgencyDetails;
import com.agency.agencydetails.repository.AgencyDetailsRepository;
import com.agency.agencydetails.service.AgencyDetailsAssembler;
import com.agency.contractmanagement.contractwork.assembler.ContractAssembler;
import com.agency.contractmanagement.contractwork.model.ContractWork;
import com.agency.contractmanagement.contractwork.repository.ContractWorkRepository;
import com.agency.contractmanagement.utils.PlaceholderResolver;
import com.agency.contractor.assembler.ContractorAssembler;
import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.documentcontext.doccontext.DocumentContext;
import com.agency.documentcontext.doccontext.GenerationResult;
import com.agency.documents.model.TemplateDocument;
import com.agency.documents.service.DocumentManagementService;
import com.agency.dto.contractwork.DocumentGenerateRequest;
import com.agency.exception.AgencyException;
import com.agency.exception.AgencyExceptionResult;
import com.agency.exception.ContractErrorResult;
import com.agency.generator.DocGenerator;
import com.agency.generator.HtmlTemplateDocGenerator;
import com.agency.generator.WordDocGenerator;
import com.agency.generator.service.FileWriterService;
import com.agency.service.ContractDocumentGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class ContractWorkGenerator implements ContractDocumentGeneratorService {

    private final ContractWorkRepository contractWorkRepository;
    private final AgencyDetailsRepository agencyDetailsRepository;
    private final DocumentManagementService documentService;
    private final FileWriterService fileWriterService;
    private final String docStaticFilePath;

    public ContractWorkGenerator(ContractWorkRepository contractWorkRepository,
                                 AgencyDetailsRepository agencyDetailsRepository,
                                 FileWriterService fileWriterService,
                                 DocumentManagementService documentService,
                                 @Value("${doc-static-file-path}") String docStaticFilePath) {
        this.contractWorkRepository = contractWorkRepository;
        this.agencyDetailsRepository = agencyDetailsRepository;
        this.documentService = documentService;
        this.fileWriterService = fileWriterService;
        this.docStaticFilePath = docStaticFilePath;
    }

    @Override
    @Transactional
    public GenerationResult generate(DocumentGenerateRequest documentGenerateRequest) {

        ContractWork contractWork = getContractWork(documentGenerateRequest.contractNumber());
        validate(contractWork);

        AgencyDetails agencyDetails = getAgencyDetails();

        UUID contractPublicID = contractWork.getPublicId();

        DocumentContext documentContext = getDocumentContext(documentGenerateRequest.docContextType(), agencyDetails, contractWork);

        TemplateDocument template = documentService.getTemplateDocument(documentGenerateRequest.templateName());
        DocGenerator docGenerator = getDocGenerator(template);
        log.info("==> Start generating document proces for contract with public id: {}", contractPublicID);
        GenerationResult generate = docGenerator.generate(documentContext);


        if (generate.isSuccess()) {
            documentService.saveGeneratedDocument(generate, contractPublicID);
            contractWork.setFilename(generate.getFilename());
            contractWorkRepository.save(contractWork);
        } else {
            documentService.saveErrorDocument(generate, contractPublicID);
        }

        log.info("==> End of generating document proces for contractor with public id: {}, new document has been created with name: {}",
                contractPublicID, generate.getFilename());

        return generate;
    }

    @NotNull
    private DocGenerator getDocGenerator(TemplateDocument template) {
        if(template.getHtmlContent() != null) {
            return new HtmlTemplateDocGenerator(docStaticFilePath, fileWriterService, template.getHtmlContent());
        }
        return new WordDocGenerator(docStaticFilePath, fileWriterService, template.getFileName());
    }

    @NotNull
    private DocumentContext getDocumentContext(DocContextType docContextType, AgencyDetails agencyDetails, ContractWork contractWork) {
        return new DocumentContext(AgencyDetailsAssembler.toDto(agencyDetails),
                ContractorAssembler.toDto(contractWork.getContractor()),
                ContractAssembler.toContractWorkDto(contractWork),
                getPlaceholderContextFields(contractWork, agencyDetails), docContextType);
    }

    private ContractWork getContractWork(String contractNumber) {
        return contractWorkRepository.findContractWorkByContractNumber(contractNumber)
                .orElseThrow(() -> new AgencyException(ContractErrorResult.CONTRACT_NOT_EXISTS, contractNumber));
    }

    private void validate(ContractWork contractWork) {
        if (contractWork.getFilename() != null) {
            throw new AgencyException(ContractErrorResult.CANNOT_GENERATE_ANOTHER_CONTRACT_DOCUMENT);
        }
    }

    private AgencyDetails getAgencyDetails() {
        return agencyDetailsRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new AgencyException(AgencyExceptionResult.AGENCY_NOT_INITIALIZED_EXCEPTION));
    }

    private Map<String, Object> getPlaceholderContextFields(ContractWork contractWork, AgencyDetails agencyDetails) {
        Map<String, Object> placeholderContextFields = new HashMap<>();
        placeholderContextFields.putAll(PlaceholderResolver.fillInPlaceholders(contractWork));
        placeholderContextFields.putAll(PlaceholderResolver.fillInPlaceholders(agencyDetails));
        placeholderContextFields.putAll(PlaceholderResolver.fillInPlaceholders(contractWork.getContractor()));

        return placeholderContextFields;
    }
}
