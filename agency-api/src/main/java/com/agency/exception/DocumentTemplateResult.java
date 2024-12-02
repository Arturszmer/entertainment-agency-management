package com.agency.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DocumentTemplateResult implements AgencyErrorResult{

    DOCUMENT_TEMPLATE_NOT_FOUND(HttpStatus.NOT_FOUND, "ADT01", "Template not found"),
    TEMPLATE_NAME_FOR_DOC_CONTEXT_EXISTS(HttpStatus.BAD_REQUEST, "ADT02", "Template name %s for doc context %s already exists"),
    TEMPLATE_FILENAME_IS_NULL(HttpStatus.BAD_REQUEST, "ADT03" , "Template filename is null"),
    FILE_NOT_SAVED(HttpStatus.BAD_REQUEST, "ADT04" , "File %s not saved"),;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
