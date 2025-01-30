package com.study.board.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(DuplicateIsbnException.class)
    public ResponseEntity<ErrorResponse> handleException(DuplicateIsbnException e) {
        return ResponseEntity.status(ErrorCode.INVALID_INPUT_VALUE.getStatus())
                .body(new ErrorResponse(e.getMessage()));
    }
}
