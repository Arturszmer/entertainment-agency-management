package com.agency.contractmanagement.service;

import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.documents.model.ContractDocument;
import com.agency.documents.repository.ContractDocumentRepository;
import com.agency.exception.AgencyException;
import com.agency.exception.ContractErrorResult;
import com.agency.generator.DirectoryGenerator;
import com.agency.service.DocumentRemoveUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@Slf4j
public class ContractWorkDocumentRemove implements DocumentRemoveUtil {

    private final ContractDocumentRepository contractDocumentRepository;
    private final String path;

    public ContractWorkDocumentRemove(ContractDocumentRepository contractDocumentRepository,
                                      @Value("${doc-static-file-path}") String path) {
        this.contractDocumentRepository = contractDocumentRepository;
        this.path = path;
    }

    @Override
    public void removeDocument(String contractPublicId) {
        ContractDocument contractDocument = contractDocumentRepository.findContractorDocumentByContractPublicId(UUID.fromString(contractPublicId))
                .orElseThrow(() -> new AgencyException(ContractErrorResult.CONTRACT_DOCUMENT_DOES_NOT_EXISTS, contractPublicId));

        try {
            Path directoryPath = new DirectoryGenerator(path, DocContextType.CONTRACT_WORK.toString()).getDirectory();
            Path existingFile = directoryPath.resolve(contractDocument.getFileName());
            if(Files.exists(existingFile)) {
                Files.delete(existingFile);
                log.info("Deleted contractor document file: {}", contractDocument.getFileName());
            } else {
                log.warn("File not found: {}", existingFile);
            }
            contractDocumentRepository.delete(contractDocument);
            log.info("Deleted contractor document from DB is finished: {}", contractDocument.getFileName());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
