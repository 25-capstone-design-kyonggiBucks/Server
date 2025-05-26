package com.capstone.exception;

public class AudioFileNotFoundException extends RuntimeException {
    public AudioFileNotFoundException(String message) {
        super(message);
    }
}
