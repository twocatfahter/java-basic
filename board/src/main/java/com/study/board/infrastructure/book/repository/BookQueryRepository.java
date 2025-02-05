package com.study.board.infrastructure.book.repository;

import com.study.board.domains.book.model.Book;
import com.study.board.service.book.dto.BookSearchCriteria;

import java.util.List;

public interface BookQueryRepository {
    List<Book> searchQueryBooks(BookSearchCriteria criteria);
}
