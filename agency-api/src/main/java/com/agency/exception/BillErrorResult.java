package com.agency.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BillErrorResult implements AgencyErrorResult {

    AMOUNT_MUST_BE_GREATER_THAN_ZERO(HttpStatus.BAD_REQUEST, "B001", "Contract balance should be greater than zero to generate a bill."),
    CONTRACT_MUST_BE_IN_ACTIVE_OR_SIGNED_STATUS(HttpStatus.BAD_REQUEST, "B002", "Contract must be in confirmed status."),;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
