package com.study.board.service.book.facade;

import com.study.board.api.dto.request.BookRequest;
import com.study.board.api.dto.request.BookSearchRequest;
import com.study.board.api.dto.response.BookResponse;
import com.study.board.domains.book.model.Book;
import com.study.board.service.book.BookService;
import com.study.board.service.book.dto.BookSearchCriteria;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookFacadeService {
    private final BookService bookService;

    @Transactional
    public BookResponse saveBook(BookRequest request) {
        Book book = bookService.saveBook(request.toServiceRequest());

        return BookResponse.from(book);
    }

    public BookResponse getBookById(@Positive(message = "ID Must be positive") Long id) {
        return BookResponse.from(bookService.getBookById(id));
    }

    @Transactional
    public BookResponse updateStock(@Positive(message = "ID Must be positive") Long id,
                                    @NotNull(message = "Quantity is required") Integer quantity) {
        return BookResponse.from(bookService.updateStock(id, quantity));
    }

    @Transactional
    public void deleteBook(@Positive(message = "ID Must be positive") Long id) {
        bookService.deleteBook(id);
    }

    public List<BookResponse> searchBooks(BookSearchRequest request) {
        BookSearchCriteria criteria = request.toCriteria();

        return bookService.searchBooks(criteria)
                .stream()
                .map(BookResponse::from)
                .toList();
    }
}
