package com.example.wid.exception.handler;

import com.example.wid.exception.AlreadyExistsMemberException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistsMemberException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(AlreadyExistsMemberException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // 409 Conflict
                .body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // 400 Bad Request
                .body(ex.getMessage());
    }
}
