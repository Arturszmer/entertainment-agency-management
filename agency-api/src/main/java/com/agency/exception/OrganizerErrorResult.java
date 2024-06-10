package com.agency.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrganizerErrorResult implements AgencyErrorResult {

    ORGANIZER_NOT_FOUND(HttpStatus.BAD_REQUEST, "AO001","Organizer with publicId %s not found."),
    CANNOT_CHANGE_NOT_YOURS_ORGANIZER(HttpStatus.BAD_REQUEST, "AO002","Organizer is ruled by %s, you cannot make any changes."),
    CANNOT_ASSIGN_TO_NOT_EXISTED_USER(HttpStatus.BAD_REQUEST, "AO003","Organizer cannot be assign to user which does not exist.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
