package com.agency.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ContractorErrorResult implements AgencyErrorResult {

    EXISTING_CONTRACT_EXCEPTION(HttpStatus.BAD_REQUEST, "AE001","You cannot delete a contractor with existing contracts. Deleting the user with the public ID %s has failed."),
    CONTRACTOR_WITH_PESEL_EXISTS(HttpStatus.BAD_REQUEST, "AE002", "The Contractor with given PESEL exists."),
    CONTRACTOR_DOES_NOT_EXISTS(HttpStatus.NOT_FOUND, "AE003","Contractor with public id %s does not exist."),
    PESEL_INVALID_EXCEPTION(HttpStatus.BAD_REQUEST, "AE004","Provided PESEL number is invalid."),
    ONLY_ONE_AGENCY_CAN_EXIST(HttpStatus.BAD_REQUEST, "AE005","Only one agency can exist in application."),
    AGENCY_NOT_INITIALIZED_EXCEPTION(HttpStatus.NOT_FOUND, "AE006", "Agency does not exist, you must initialize the agency."),
    PROJECT_DOES_NOT_EXIST_EXCEPTION(HttpStatus.NOT_FOUND, "AE007","Project with contract number %s does not exist."),
    PROJECT_CANNOT_CHANGE_SIGN_OR_TERMINATE_STATUS(HttpStatus.BAD_REQUEST, "AE008", "Project with status SIGNED or TERMINATED cannot be change by this way. Use revert option."),
    CONTRACT_TYPE_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "AE008","Project with type %s does not exist."),
    CANNOT_CREATE_CONTRACT_WORK_FOR_TERMINATED_PROJECT(HttpStatus.BAD_REQUEST, "AE009","Cannot create contract work for terminated Project.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
