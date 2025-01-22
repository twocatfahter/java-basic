package com.study.board.service.book;

import com.study.board.domains.book.model.Book;
import com.study.board.service.book.dto.BookServiceRequest;

public interface BookService {
    Book createBook(BookServiceRequest request);
}
