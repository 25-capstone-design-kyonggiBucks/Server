package com.capstone.exception;

public class VideoNotFoundInDatabaseException extends RuntimeException {
    public VideoNotFoundInDatabaseException(String message) {
        super(message);
    }
}
