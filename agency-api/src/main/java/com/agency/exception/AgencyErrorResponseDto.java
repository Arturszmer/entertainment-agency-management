package com.agency.exception;

import org.springframework.http.HttpStatus;

public record AgencyErrorResponseDto(HttpStatus status, String message) {

}
