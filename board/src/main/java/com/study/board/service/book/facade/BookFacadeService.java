package com.study.board.service.book.facade;

import com.study.board.api.book.dto.request.BookRequest;
import com.study.board.api.book.dto.request.BookSearchRequest;
import com.study.board.api.book.dto.request.BookUpdateRequest;
import com.study.board.api.book.dto.request.BookWithCategoryRequest;
import com.study.board.api.book.dto.response.BookResponse;
import com.study.board.domains.book.model.Book;
import com.study.board.service.book.BookService;
import com.study.board.service.book.dto.BookSearchCriteria;
import com.study.board.service.book.dto.BookServiceWithCategoryRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookFacadeService {
    private final BookService bookService;

    @Transactional
    public BookResponse saveBook(BookRequest request) {
        validateBookRequest(request);
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

    public BookResponse updateBookDetails(Long id, @Valid BookUpdateRequest request) {
        return BookResponse.from(bookService.updateBookDetails(id, request.toServiceRequest()));
    }

    public List<BookResponse> getBooksByAuthor(String author) {
        return bookService.getBooksByAuthor(author).stream()
                .map(BookResponse::from)
                .toList();
    }

    public BookResponse createBookWithCategory(@Valid BookWithCategoryRequest request) {
        validateBookRequest(request);
        Book bookWithCategory =
                bookService.createBookWithCategory(toServiceRequest(request), request.categoryId());

        log.info("category name: {}", bookWithCategory.getCategory().getName());
        log.info("category code: {}", bookWithCategory.getCategory().getCode());
        return BookResponse.from(bookWithCategory);
    }

    private void validateBookRequest(BookRequest request) {
        if (request.price() < 0) {
            throw new IllegalArgumentException("가격이 0 보다 작을 수는 없습니다.");
        }

        if (request.stockQuantity() < 0) {
            throw new IllegalArgumentException("수량이 0보다 작을 수는 없습니다.");
        }

    }

    private void validateBookRequest(BookWithCategoryRequest request) {
        if (request.price() < 0) {
            throw new IllegalArgumentException("가격이 0 보다 작을 수는 없습니다.");
        }

        if (request.stockQuantity() < 0) {
            throw new IllegalArgumentException("수량이 0보다 작을 수는 없습니다.");
        }

    }

    private BookServiceWithCategoryRequest toServiceRequest(BookWithCategoryRequest request) {
        return new BookServiceWithCategoryRequest(
                request.title(),
                request.author(),
                request.isbn(),
                request.price(),
                request.stockQuantity(),
                request.categoryId()
        );
    }
}
