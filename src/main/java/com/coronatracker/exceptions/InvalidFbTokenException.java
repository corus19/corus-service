package com.coronatracker.exceptions;

public class InvalidFbTokenException extends RuntimeException {
    public InvalidFbTokenException(final String message){
        super(message);
    }
}
