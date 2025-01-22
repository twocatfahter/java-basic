package com.study.board.api.dto.response;

import com.study.board.domains.book.model.Book;

public record BookResponse(
        Long id,
        String title,
        String author,
        String isbn,
        Integer price,
        Integer stockQuantity
) {

    public static BookResponse from(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPrice(),
                book.getStockQuantity()
        );
    }
}
