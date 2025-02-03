package com.study.board.api.book.dto.request;

import com.study.board.service.book.dto.BookServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookRequest(
        @NotBlank(message = "Title is mandatory")
        String title,
        @NotBlank(message = "Author is mandatory")
        String author,
        @NotBlank(message = "Isbn is mandatory")
        String isbn,
        @NotNull(message = "Price is mandatory")
        Integer price,
        @NotNull(message = "stockQuantity is mandatory")
        Integer stockQuantity
) {
    public BookServiceRequest toServiceRequest() {
        return new BookServiceRequest(
                this.title,
                this.author,
                this.isbn,
                this.price,
                this.stockQuantity
        );
    }
}
