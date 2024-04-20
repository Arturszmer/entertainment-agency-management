package com.agency.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AgencyErrorResult {

    EXISTING_CONTRACT_EXCEPTION(HttpStatus.BAD_REQUEST, "You cannot delete a contractor with existing contracts. Deleting the user with the public ID %s has failed."),
    CONTRACTOR_WITH_PESEL_EXISTS(HttpStatus.BAD_REQUEST, "The Contractor with given PESEL exists."),
    CONTRACTOR_DOES_NOT_EXISTS(HttpStatus.NOT_FOUND, "Contractor with public id %s does not exist.");


    private final HttpStatus status;
    private final String message;


}
