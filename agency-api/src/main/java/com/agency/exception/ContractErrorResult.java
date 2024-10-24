package com.agency.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ContractErrorResult implements AgencyErrorResult{

    CONTRACT_DATE_PERIOD_ERROR(HttpStatus.BAD_REQUEST, "ACW001", "The contract date period is incorrect; it must fall within the project's date range."),
    CONTRACT_DATE_ERROR(HttpStatus.BAD_REQUEST, "ACW002", "The contract date period is incorrect; Start date cannot be after end date."),
    CONTRACT_SING_DATE_ERROR(HttpStatus.BAD_REQUEST, "ACW003", "The contract sign date is incorrect; it cannot be after the beginning of the contract."),
    PROJECT_IS_TERMINATED(HttpStatus.BAD_REQUEST, "ACW004", "Cannot create a contract based on the terminated project."),
    CONTRACTOR_IS_NOT_PART_OF_THE_PROJECT(HttpStatus.BAD_REQUEST, "ACW005", "Contractor %s %s is not part of the project no.: %s"),
    CONTRACT_NOT_EXISTS(HttpStatus.NOT_FOUND, "ACW006" , "The contract with public id %s does not exist" ),
    CONTRACT_CANNOT_BE_DELETED(HttpStatus.BAD_REQUEST, "ACW007" , "Only the contract with DRAFT status can be deleted"),;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
