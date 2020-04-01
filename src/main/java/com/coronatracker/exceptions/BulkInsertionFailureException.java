package com.coronatracker.exceptions;

/**
 * Created by Sampath Katari on 29/03/20.
 */
public class BulkInsertionFailureException extends RuntimeException {
    public BulkInsertionFailureException(final String message){
        super(message);
    }
}
