package com.example.wid.controller.exception;

public class VerifierNotFoundException extends RuntimeException {
    static final String MESSAGE = "제출 대상을 찾을 수 없습니다.";
    public VerifierNotFoundException(String message){
        super(message);
    }
    public VerifierNotFoundException(){
        super(MESSAGE);
    }
}
