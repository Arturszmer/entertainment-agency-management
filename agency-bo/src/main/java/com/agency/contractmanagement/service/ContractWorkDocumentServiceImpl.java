package com.agency.contractmanagement.service;

import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.documents.model.ContractDocument;
import com.agency.documents.repository.ContractDocumentRepository;
import com.agency.exception.AgencyException;
import com.agency.exception.ContractErrorResult;
import com.agency.generator.service.FileDownloadService;
import com.agency.generator.service.FileRemoveService;
import com.agency.service.ContractWorkDocumentService;
import com.agency.user.helper.SecurityContextUsers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContractWorkDocumentServiceImpl implements ContractWorkDocumentService {

    private final ContractDocumentRepository contractDocumentRepository;
    private final FileRemoveService fileRemoveService;
    private final FileDownloadService fileDownloadService;

    @Override
    public Resource downloadDocument(String filename) {
        String usernameFromAuthenticatedUser = SecurityContextUsers.getUsernameFromAuthenticatedUser();
        return fileDownloadService.downloadFile(filename, DocContextType.CONTRACT_WORK, usernameFromAuthenticatedUser);
    }

    @Override
    public void removeDocument(String referenceId) {
        ContractDocument contractDocument = contractDocumentRepository.findContractorDocumentByContractPublicId(UUID.fromString(referenceId))
                .orElseThrow(() -> new AgencyException(ContractErrorResult.CONTRACT_DOCUMENT_DOES_NOT_EXISTS, referenceId));

        fileRemoveService.removeFile(contractDocument.getFileName(), DocContextType.CONTRACT_WORK.toString());
        contractDocumentRepository.delete(contractDocument);
        log.info("Deleted contractor document from DB is finished: {}", contractDocument.getFileName());
    }
}
