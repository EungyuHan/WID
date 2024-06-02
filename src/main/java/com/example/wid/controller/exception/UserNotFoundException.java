package com.example.wid.controller.exception;

public class UserNotFoundException extends RuntimeException {
    static final String MESSAGE = "사용자를 찾을 수 없습니다.";
    public UserNotFoundException(String message){
        super(message);
    }
    public UserNotFoundException(){
        super(MESSAGE);
    }
}
