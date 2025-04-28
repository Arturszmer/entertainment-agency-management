package com.agency.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DocumentErrorResult implements AgencyErrorResult{

    DOCUMENT_FILE_WRITE_ERROR(HttpStatus.BAD_REQUEST, "D001", "Document file write error. File is not saved"),
    DOCUMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "D002", "Document not found"),
    DOCUMENT_FILE_READ_ERROR(HttpStatus.BAD_REQUEST, "D003", "Document file read error"),
    DOCUMENT_NOT_GENERATED_SUCCESSFULLY(HttpStatus.BAD_REQUEST, "D004", "Document not generated successfully"),;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
