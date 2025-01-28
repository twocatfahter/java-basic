package com.study.board.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookWithCategoryRequest(
        @NotBlank(message = "Title is mandatory")
        String title,
        @NotBlank(message = "Author is mandatory")
        String author,
        @NotBlank(message = "Isbn is mandatory")
        String isbn,
        @NotNull(message = "Price is mandatory")
        Integer price,
        @NotNull(message = "stockQuantity is mandatory")
        Integer stockQuantity,
        @NotNull(message = "Category ID is mandatory")
        Long categoryId
) {
}
