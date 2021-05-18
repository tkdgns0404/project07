package com.example.manager.common;

public class UploadFailureException extends RuntimeException {
    public UploadFailureException(String message) {
        super(message);
    }
}
