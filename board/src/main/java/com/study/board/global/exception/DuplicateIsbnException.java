package com.study.board.global.exception;

public class DuplicateIsbnException extends RuntimeException{
    public DuplicateIsbnException(String message) {
        super(message);
    }
}
