package com.example.wid.controller.exception;

public class AlreadyExistsMemberException extends RuntimeException {
    private final static String MESSAGE = "이미 존재하는 회원입니다.";

    public AlreadyExistsMemberException(){
        super(MESSAGE);
    }
}
