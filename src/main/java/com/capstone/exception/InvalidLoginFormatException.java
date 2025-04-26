package com.capstone.exception;

public class InvalidLoginFormatException extends RuntimeException {
    public InvalidLoginFormatException(String message) {
        super(message);
    }
}
