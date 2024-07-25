package com.agency.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProjectErrorResult implements AgencyErrorResult {

    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "AP001", "Project with number %s does not exist"),
    CONTRACTOR_EXISTS_INTO_PROJECT(HttpStatus.BAD_REQUEST, "AP002" ,"Contractor with public id %s exists into the project" );

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
