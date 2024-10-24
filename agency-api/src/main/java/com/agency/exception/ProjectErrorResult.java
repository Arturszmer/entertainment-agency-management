package com.agency.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProjectErrorResult implements AgencyErrorResult {

    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "AP001", "Project with number %s does not exist"),
    CONTRACTOR_NOT_EXISTS_INTO_PROJECT(HttpStatus.BAD_REQUEST, "AP002" ,"Contractor with public id %s not exists into the project with number %s"),
    CONTRACTOR_HAS_ACTIVE_CONTRACT_IN_PROJECT(HttpStatus.BAD_REQUEST, "AP003" ,"Contractor with public id %s has an active contract in project with number %s, if you want to remove contractor first your have to remove the contract"),
    CANNOT_DELETE_PROJECT(HttpStatus.BAD_REQUEST, "AP004" ,"You can delete project. Reason: %s"),;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
