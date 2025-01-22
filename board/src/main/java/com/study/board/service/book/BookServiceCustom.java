package com.study.board.service.book;

import com.study.board.domains.book.model.Book;
import com.study.board.infrastructure.book.entity.BookJpaEntity;
import com.study.board.infrastructure.book.repository.BookJpaRepository;
import com.study.board.service.book.dto.BookServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceCustom implements BookService{
    private final BookJpaRepository bookJpaRepository;

    @Override
    public Book createBook(BookServiceRequest request) {
        Book book = Book.create(
                request.title(),
                request.author(),
                request.isbn(),
                request.price(),
                request.stockQuantity()
        );

        BookJpaEntity entity = bookJpaRepository.save(BookJpaEntity.from(book));
        return Book.from(entity);
    }
}
