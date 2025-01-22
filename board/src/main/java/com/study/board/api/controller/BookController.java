package com.study.board.api.controller;

import com.study.board.api.dto.request.BookRequest;
import com.study.board.api.dto.response.BookResponse;
import com.study.board.service.book.facade.BookFacadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Tag(name = "Book", description = "Book API")
public class BookController {
    private final BookFacadeService bookFacadeService;

    @PostMapping
    @Operation(summary = "책 등록")
    public ResponseEntity<BookResponse> saveBook(@Valid @RequestBody BookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookFacadeService.createBook(request));
    }

}
