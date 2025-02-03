package com.study.board.api.book.dto.request;

import com.study.board.service.book.dto.BookSearchCriteria;

public record BookSearchRequest(
        String title,
        String author,
        Integer minPrice,
        Integer maxPrice
) {
    public BookSearchCriteria toCriteria() {
        return BookSearchCriteria.builder()
                .title(title)
                .author(author)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();
    }
}
