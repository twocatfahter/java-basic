package com.study.board.service.book.facade;

import com.study.board.api.dto.request.BookRequest;
import com.study.board.api.dto.response.BookResponse;
import com.study.board.domains.book.model.Book;
import com.study.board.service.book.BookService;
import com.study.board.service.book.dto.BookServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookFacadeService {
    private final BookService bookService;

    @Transactional
    public BookResponse createBook(BookRequest request) {
        Book book = bookService.createBook(request.toServiceRequest());

        return BookResponse.from(book);
    }
}
