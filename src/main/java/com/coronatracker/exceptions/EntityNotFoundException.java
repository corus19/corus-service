package com.coronatracker.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(final String message){
        super(message);
    }
}
