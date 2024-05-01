package com.example.wid.exception;

public class AlreadyExistsMemberException extends RuntimeException{
    private final static String message = "Member already exists";

    public AlreadyExistsMemberException(){
        super(message);
    }
}
