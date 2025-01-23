package com.study.board.service.book;

import com.study.board.domains.book.model.Book;
import com.study.board.service.book.dto.BookServiceRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public interface BookService {
    Book saveBook(BookServiceRequest request);

    Book getBookById(@Positive(message = "ID Must be positive") Long id);

    Book updateStock(@Positive(message = "ID Must be positive") Long id, @NotNull(message = "Quantity is required") Integer quantity);

    void deleteBook(@Positive(message = "ID Must be positive") Long id);
}
