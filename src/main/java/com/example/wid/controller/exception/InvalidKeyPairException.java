package com.example.wid.controller.exception;

public class InvalidKeyPairException extends RuntimeException {
    private final static String MESSAGE = "유효하지 않은 키쌍입니다.";

    public InvalidKeyPairException(){
        super(MESSAGE);
    }
}
