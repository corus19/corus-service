package com.coronatracker.exceptions;

import com.coronatracker.util.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionalHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {EntityNotFoundException.class, Exception.class, InvalidJwtTokenException.class, InvalidFbTokenException.class})
    protected ResponseEntity entityNotFoundException(final RuntimeException runtimeException){
        return ResponseEntity.badRequest().body(ErrorResponse.builder().errorMessage(runtimeException.getMessage()).build());
    }

}
