package com.study.board.api.book.dto.request;

import com.study.board.service.book.dto.BookServiceRequest;
import com.study.board.service.book.dto.BookUpdateServiceRequest;

public record BookUpdateRequest(
        Integer price,
        Integer stockQuantity
) {
    public BookUpdateServiceRequest toServiceRequest() {
        return new BookUpdateServiceRequest(price, stockQuantity);
    }
}
