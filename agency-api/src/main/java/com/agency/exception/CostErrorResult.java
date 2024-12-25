package com.agency.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CostErrorResult implements AgencyErrorResult {

    GENERATED_COST_CANNOT_BE_CHANGED(HttpStatus.BAD_REQUEST, "C001", "Generated cost cannot be changed"),
    VALUE_CANNOT_BE_NEGATIVE(HttpStatus.BAD_REQUEST, "C002", "Value cannot be negative"),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
