package com.example.wid.controller.exception;

public class EncryptionException extends RuntimeException{
    public EncryptionException(String message) {
        super(message);
    }
}