package com.example.wid.controller.handler;

import com.example.wid.controller.exception.AlreadyExistsMemberException;
import com.example.wid.controller.exception.InvalidCertificateException;
import com.example.wid.controller.exception.InvalidKeyPairException;
import com.example.wid.controller.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistsMemberException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(AlreadyExistsMemberException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(InvalidKeyPairException.class)
    public ResponseEntity<String> handleInvalidKeyPairException(InvalidKeyPairException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(InvalidCertificateException.class)
    public ResponseEntity<String> handleInvalidCertificateException(InvalidCertificateException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}
