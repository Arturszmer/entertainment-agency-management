package com.agency.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AgencyExceptionResult implements AgencyErrorResult {

    AGENCY_NOT_INITIALIZED_EXCEPTION(HttpStatus.NOT_FOUND, "AE006", "Agency does not exist, you must initialize the agency."),
    ONLY_ONE_AGENCY_CAN_EXIST(HttpStatus.BAD_REQUEST, "AE005", "Only one agency can exist in application.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
