package com.agency.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum UserExceptionResult implements AgencyErrorResult{

    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER001","User %s is not found"),
    NEW_PASSWORD_DOES_NOT_MATCH(HttpStatus.BAD_REQUEST, "USER002","New password is not matched with confirmation password."),
    CURRENT_PASSWORD_DOES_NOT_MATCH(HttpStatus.BAD_REQUEST, "USER003","The current password is incorrect."),
    NEW_PASSWORD_MUST_BE_DIFFERENT_THAN_CURRENT(HttpStatus.BAD_REQUEST, "USER004","The new password must be different than the old one.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
