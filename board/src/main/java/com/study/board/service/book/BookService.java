package com.study.board.service.book;

import com.study.board.domains.book.model.Book;
import com.study.board.service.book.dto.BookSearchCriteria;
import com.study.board.service.book.dto.BookServiceRequest;
import com.study.board.service.book.dto.BookServiceWithCategoryRequest;
import com.study.board.service.book.dto.BookUpdateServiceRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Collection;
import java.util.List;

public interface BookService {
    Book saveBook(BookServiceRequest request);

    Book getBookById(@Positive(message = "ID Must be positive") Long id);

    Book updateStock(@Positive(message = "ID Must be positive") Long id, @NotNull(message = "Quantity is required") Integer quantity);

    void deleteBook(@Positive(message = "ID Must be positive") Long id);

    List<Book> searchBooks(BookSearchCriteria criteria);
    List<Book> searchQueryBooks(BookSearchCriteria criteria);

    Book updateBookDetails(Long id, BookUpdateServiceRequest serviceRequest);

    List<Book> getBooksByAuthor(String author);

    Book createBookWithCategory(BookServiceWithCategoryRequest serviceRequest, @NotNull(message = "Category ID is mandatory") Long categoryId);

}
