package com.study.board.service.book.dto;

public record BookUpdateServiceRequest(
        Integer price,
        Integer stockQuantity
) {
}
