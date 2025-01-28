package com.study.board.api.controller;

import com.study.board.api.dto.request.BookRequest;
import com.study.board.api.dto.request.BookSearchRequest;
import com.study.board.api.dto.request.BookUpdateRequest;
import com.study.board.api.dto.request.BookWithCategoryRequest;
import com.study.board.api.dto.response.BookResponse;
import com.study.board.service.book.facade.BookFacadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Tag(name = "Book", description = "Book API")
public class BookController {
    private final BookFacadeService bookFacadeService;

    @PostMapping
    @Operation(summary = "책 등록")
    public ResponseEntity<BookResponse> saveBook(@Valid @RequestBody BookRequest request) {
        return ResponseEntity.status(201).body(bookFacadeService.saveBook(request));
    }

    @PostMapping("/category")
    @Operation(summary = "책 등록", description = "카테고리 포함된 책 등록")
    public ResponseEntity<BookResponse> createBookWithCategory(
            @Valid @RequestBody BookWithCategoryRequest request
    ) {
        return ResponseEntity.ok(bookFacadeService.createBookWithCategory(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Book by Id", description = "책 상세 정보를 아이디 값 기준으로 가져온다")
    public ResponseEntity<BookResponse> getBookById(
            @PathVariable @Positive(message = "ID Must be positive") Long id
            ) {
        return ResponseEntity.ok(bookFacadeService.getBookById(id));

    }

    @PutMapping("/{id}/stock")
    @Operation(summary = "Update stock", description = "Upate book stock quantity")
    public ResponseEntity<BookResponse> updateStock(
            @PathVariable @Positive(message = "ID Must be positive") Long id,
            @RequestParam @NotNull(message = "Quantity is required") Integer quantity
            ) {
        return ResponseEntity.ok(bookFacadeService.updateStock(id, quantity));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Book", description = "책을 삭제합니다 아이디 기준으로")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(
            @PathVariable @Positive(message = "ID Must be positive") Long id
    ) {
        bookFacadeService.deleteBook(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Search books by criteria")
    public ResponseEntity<List<BookResponse>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice
    ) {
        BookSearchRequest request = new BookSearchRequest(title, author, minPrice, maxPrice);
        return ResponseEntity.ok(bookFacadeService.searchBooks(request));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update book details")
    public ResponseEntity<BookResponse> updateBookDetails(
            @PathVariable Long id,
            @Valid @RequestBody BookUpdateRequest request
    ) {
        return ResponseEntity.ok(bookFacadeService.updateBookDetails(id, request));
    }

    @GetMapping("/author/{author}")
    @Operation(summary = "저자 기준으로 책을 찾는다")
    public ResponseEntity<List<BookResponse>> getBooksByAuthor(
            @PathVariable String author
    ) {
        return ResponseEntity.ok(bookFacadeService.getBooksByAuthor(author));
    }

}
