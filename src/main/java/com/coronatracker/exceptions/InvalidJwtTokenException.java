package com.coronatracker.exceptions;

public class InvalidJwtTokenException extends RuntimeException {
    public InvalidJwtTokenException(final String message){
        super(message);
    }
}