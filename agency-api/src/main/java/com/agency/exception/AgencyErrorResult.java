package com.agency.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AgencyErrorResult {

    EXISTING_CONTRACT_EXCEPTION(HttpStatus.BAD_REQUEST, "You cannot delete a contractor with existing contracts. Deleting the user with the public ID %s has failed."),
    CONTRACTOR_WITH_PESEL_EXISTS(HttpStatus.BAD_REQUEST, "The Contractor with given PESEL exists."),
    CONTRACTOR_DOES_NOT_EXISTS(HttpStatus.NOT_FOUND, "Contractor with public id %s does not exist."),
    PESEL_INVALID_EXCEPTION(HttpStatus.BAD_REQUEST, "Provided PESEL number is invalid."),
    ONLY_ONE_AGENCY_CAN_EXIST(HttpStatus.BAD_REQUEST, "Only one agency can exist in application."),
    AGENCY_NOT_INITIALIZED_EXCEPTION(HttpStatus.NOT_FOUND, "Agency does not exist, you must initialize the agency."),
    PROJECT_DOES_NOT_EXIST_EXCEPTION(HttpStatus.NOT_FOUND, "Project with contract number %s does not exist."),
    PROJECT_CANNOT_CHANGE_SIGN_OR_TERMINATE_STATUS(HttpStatus.BAD_REQUEST, "Project with status SIGNED or TERMINATED cannot be change by this way. Use revert option.");

    private final HttpStatus status;
    private final String message;


}
