package com.agency.contractmanagement.service;

import com.agency.agencydetails.model.AgencyDetails;
import com.agency.agencydetails.repository.AgencyDetailsRepository;
import com.agency.agencydetails.service.AgencyDetailsAssembler;
import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.documentcontext.doccontext.DocumentContext;
import com.agency.documentcontext.doccontext.GenerationResult;
import com.agency.contractmanagement.assembler.ContractAssembler;
import com.agency.contractmanagement.model.ContractWork;
import com.agency.contractmanagement.repository.ContractWorkRepository;
import com.agency.contractmanagement.utils.AgencyPlaceholderGenerator;
import com.agency.contractmanagement.utils.ContractWorkPlaceholderGenerator;
import com.agency.contractor.assembler.ContractorAssembler;
import com.agency.documents.model.ContractDocument;
import com.agency.documents.model.TemplateDocument;
import com.agency.documents.repository.ContractDocumentRepository;
import com.agency.documents.repository.TemplateDocumentRepository;
import com.agency.dto.contractwork.DocumentGenerateRequest;
import com.agency.exception.AgencyException;
import com.agency.exception.AgencyExceptionResult;
import com.agency.exception.ContractErrorResult;
import com.agency.exception.DocumentTemplateResult;
import com.agency.generator.DocGenerator;
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
    private final ContractDocumentRepository contractDocumentRepository;
    private final TemplateDocumentRepository templateDocumentRepository;
    private final FileWriterService fileWriterService;
    private final String docStaticFilePath;

    public ContractWorkGenerator(ContractWorkRepository contractWorkRepository,
                                 AgencyDetailsRepository agencyDetailsRepository,
                                 ContractDocumentRepository contractDocumentRepository,
                                 TemplateDocumentRepository templateDocumentRepository, FileWriterService fileWriterService,
                                 @Value("${doc-static-file-path}") String docStaticFilePath) {
        this.contractWorkRepository = contractWorkRepository;
        this.agencyDetailsRepository = agencyDetailsRepository;
        this.contractDocumentRepository = contractDocumentRepository;
        this.templateDocumentRepository = templateDocumentRepository;
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

        TemplateDocument template = getTemplate(documentGenerateRequest.templateName());
        DocGenerator docGenerator = new DocGenerator(docStaticFilePath, template.getFileName(), fileWriterService);
        log.info("==> Start generating document proces for contract with public id: {}", contractPublicID);
        GenerationResult generate = docGenerator.generate(documentContext);

        if(generate.isSuccess()){
            ContractDocument contractDocument = new ContractDocument(generate.getFilename(), contractPublicID);
            contractDocumentRepository.save(contractDocument);
            contractWork.setFilename(generate.getFilename());
            contractWorkRepository.save(contractWork);
        } else {
            ContractDocument contractDocument = new ContractDocument(generate.getFilename(), contractPublicID, true);
            contractDocumentRepository.save(contractDocument);
        }

        log.info("==> End of generating document proces for contractor with public id: {}, new document has been created with name: {}",
                contractPublicID, generate.getFilename());

        return generate;
    }

    private TemplateDocument getTemplate(String templateName) {
        return templateDocumentRepository.findByTemplateName(templateName)
                .orElseThrow(() -> new AgencyException(DocumentTemplateResult.DOCUMENT_TEMPLATE_NOT_FOUND));
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
        if(contractWork.getFilename() != null){
            throw new AgencyException(ContractErrorResult.CANNOT_GENERATE_ANOTHER_CONTRACT_DOCUMENT);
        }
    }

    private AgencyDetails getAgencyDetails() {
        return agencyDetailsRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new AgencyException(AgencyExceptionResult.AGENCY_NOT_INITIALIZED_EXCEPTION));
    }

    private Map<String, Object> getPlaceholderContextFields(ContractWork contractWork, AgencyDetails agencyDetails) {
        Map<String, Object> placeholderContextFields = new HashMap<>();
        placeholderContextFields.putAll(new ContractWorkPlaceholderGenerator().getPlaceholders(contractWork.getClass(), contractWork));
        placeholderContextFields.putAll(new AgencyPlaceholderGenerator().getPlaceholders(agencyDetails.getClass(), agencyDetails));
        placeholderContextFields.putAll(new ContractWorkPlaceholderGenerator().getPlaceholders(contractWork.getContractor().getClass(), contractWork.getContractor()));
        return placeholderContextFields;
    }
}
