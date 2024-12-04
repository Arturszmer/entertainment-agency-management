package com.agency.contractmanagement.service;

import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.documents.model.ContractDocument;
import com.agency.documents.repository.ContractDocumentRepository;
import com.agency.exception.AgencyException;
import com.agency.exception.ContractErrorResult;
import com.agency.generator.service.FileRemoveService;
import com.agency.service.DocumentRemoveUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ContractWorkDocumentRemoveService implements DocumentRemoveUtil {

    private final ContractDocumentRepository contractDocumentRepository;
    private final FileRemoveService fileRemoveService;

    public ContractWorkDocumentRemoveService(ContractDocumentRepository contractDocumentRepository, FileRemoveService fileRemoveService) {
        this.contractDocumentRepository = contractDocumentRepository;
        this.fileRemoveService = fileRemoveService;
    }

    @Override
    public void removeDocument(String contractPublicId) {
        ContractDocument contractDocument = contractDocumentRepository.findContractorDocumentByContractPublicId(UUID.fromString(contractPublicId))
                .orElseThrow(() -> new AgencyException(ContractErrorResult.CONTRACT_DOCUMENT_DOES_NOT_EXISTS, contractPublicId));

        fileRemoveService.removeFile(contractDocument.getFileName(), DocContextType.CONTRACT_WORK.toString());
        contractDocumentRepository.delete(contractDocument);
        log.info("Deleted contractor document from DB is finished: {}", contractDocument.getFileName());
    }
}
