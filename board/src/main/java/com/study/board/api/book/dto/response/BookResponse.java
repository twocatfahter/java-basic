package com.study.board.api.book.dto.response;

import com.study.board.domains.book.model.Book;

public record BookResponse(
        Long id,
        String title,
        String author,
        String isbn,
        Integer price,
        Integer stockQuantity,
        String category
) {

    public static BookResponse from(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPrice(),
                book.getStockQuantity(),
                book.getCategory() != null ? book.getCategory().getName() : null
        );
    }
}
