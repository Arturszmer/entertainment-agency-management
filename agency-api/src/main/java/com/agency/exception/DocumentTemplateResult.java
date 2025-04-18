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
    FILE_NOT_SAVED(HttpStatus.BAD_REQUEST, "ADT04" , "File %s not saved"),
    TEMPLATE_FILENAME_IS_NOT_UNIQUE(HttpStatus.BAD_REQUEST, "ADT05", "File with the name %s exists"),
    TEMPLATE_FILENAME_MUST_BE_EQUAL(HttpStatus.BAD_REQUEST, "ADT06", "Filename %s must be equal to %s"),
    TEMPLATE_FILENAME_EXISTS_IN_OTHER_TEMPLATE(HttpStatus.BAD_REQUEST, "ADT07", "Filename %s already exists in other template"),;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
