package com.agency.exception;

import org.springframework.http.HttpStatus;

public interface AgencyErrorResult {

    HttpStatus getHttpStatus();
    String getCode();
    String getMessage();
}
