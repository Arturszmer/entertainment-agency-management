package com.agency.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DocumentErrorResult implements AgencyErrorResult{

    DOCUMENT_FILE_WRITE_ERROR(HttpStatus.BAD_REQUEST, "D001", "Document file write error. File is not saved"),;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
