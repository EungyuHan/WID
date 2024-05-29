package com.example.wid.controller.exception;

public class InvalidCertificateException extends RuntimeException {
    public InvalidCertificateException(String message){
        super(message);
    }
}
