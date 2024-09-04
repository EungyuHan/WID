package com.example.wid.controller.exception;

public class InvalidFolderException extends RuntimeException{
    final static String MESSAGE = "유효하지 않은 폴더입니다.";
    public InvalidFolderException() {
        super(MESSAGE);
    }
}
