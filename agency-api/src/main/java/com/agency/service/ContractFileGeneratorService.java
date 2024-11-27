package com.agency.service;

import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.documentcontext.doccontext.GenerationResult;

public interface ContractFileGeneratorService {

    GenerationResult generate(String contractPublicId, String templateFilename, DocContextType contextType);
}
