package com.agency.dto.contractwork;

import com.agency.documentcontext.doccontext.DocContextType;

public record DocumentGenerateRequest(
        String contractNumber,
        String templateName,
        DocContextType docContextType
) {
}
