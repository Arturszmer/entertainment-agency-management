package com.agency.service;

import com.agency.documentcontext.doccontext.GenerationResult;
import com.agency.dto.contractwork.DocumentGenerateRequest;

public interface ContractDocumentGeneratorService {

    GenerationResult generate(DocumentGenerateRequest documentGenerateRequest);
}
