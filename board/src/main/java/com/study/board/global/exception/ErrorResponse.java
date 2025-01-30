package com.study.board.global.exception;

public record ErrorResponse(
        String message,
        String code
) {
    public ErrorResponse(String message) {
        this(message, null);
    }

    public ErrorResponse(String message, String code) {
        this.message = message;
        this.code = code;
    }
}
