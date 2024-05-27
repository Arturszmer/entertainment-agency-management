package com.agency.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AgencyException extends RuntimeException{

    private final HttpStatus status;
    private final String errorCode;

    public AgencyException(AgencyErrorResult errorResult, Object ...o) {
        super(String.format(errorResult.getMessage(), o));
        this.status = errorResult.getHttpStatus();
        this.errorCode = errorResult.getCode();
    }
}
