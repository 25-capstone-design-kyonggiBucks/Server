package com.capstone.exception;

public class AudioNotFoundInDatabaseException extends RuntimeException {
    public AudioNotFoundInDatabaseException(String message) {
        super(message);
    }
}
