package com.agency.contractmanagement.contractwork.service;

import com.agency.documents.model.ContractHtmlDocument;
import com.agency.documents.repository.ContractHtmlDocumentRepository;
import com.agency.exception.AgencyException;
import com.agency.exception.ContractErrorResult;
import com.agency.service.DocumentHtmlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContractWorkDocumentHtmlService implements DocumentHtmlService {

    private final ContractHtmlDocumentRepository contractHtmlDocumentRepository;

    @Override
    public String getDetails(UUID contractPublicId) {
        ContractHtmlDocument contractHtmlDocument = contractHtmlDocumentRepository.findByContractPublicId(contractPublicId)
                .orElseThrow(() -> new AgencyException(ContractErrorResult.CONTRACT_DOCUMENT_DOES_NOT_EXISTS, contractPublicId));

        return contractHtmlDocument.getContent();
    }
}
