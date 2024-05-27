package com.agency.controller;

import com.agency.exception.AgencyErrorResponseDto;
import com.agency.exception.AgencyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class AgencyExceptionHandler {

    @ExceptionHandler(AgencyException.class)
    public ResponseEntity<AgencyErrorResponseDto> handleAgencyException(final AgencyException exception){
        log.warn("Agency exception occur: ", exception);
        return ResponseEntity.status(exception.getStatus()).body(
                new AgencyErrorResponseDto(exception.getStatus(), exception.getErrorCode(), exception.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<AgencyErrorResponseDto> handleException(final RuntimeException exception){
        log.warn("Exception occur: ", exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new AgencyErrorResponseDto(HttpStatus.BAD_REQUEST, "ERR001", exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
