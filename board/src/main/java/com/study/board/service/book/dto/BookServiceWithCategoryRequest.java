package com.study.board.service.book.dto;

public record BookServiceWithCategoryRequest(
        String title,
        String author,
        String isbn,
        int price,
        int stockQuantity,
        Long categoryId
) {
}
