package com.study.board.service.book;

import com.study.board.domains.book.model.Book;
import com.study.board.infrastructure.book.entity.BookJpaEntity;
import com.study.board.infrastructure.book.repository.BookJpaRepository;
import com.study.board.service.book.dto.BookSearchCriteria;
import com.study.board.service.book.dto.BookServiceRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceCustom implements BookService{
    private final BookJpaRepository bookJpaRepository;

    @Override
    @Transactional
    public Book saveBook(BookServiceRequest request) {
        Book book = Book.create(
                request.title(),
                request.author(),
                request.isbn(),
                request.price(),
                request.stockQuantity()
        );

        BookJpaEntity savedEntity = bookJpaRepository.save(BookJpaEntity.from(book));

        return Book.from(savedEntity);
    }

    @Override
    public Book getBookById(Long id) {
        return Book.from(findActiveBookEntityById(id));
    }

    @Override
    @Transactional
    public Book updateStock(Long id, Integer quantity) {
        BookJpaEntity entity = findActiveBookEntityById(id);

        Book book = Book.from(entity);
        book.updateStock(quantity);
        entity.update(book);
        return Book.from(entity);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        BookJpaEntity entity = findActiveBookEntityById(id);
        entity.delete();
    }

    @Override
    public List<Book> searchBooks(BookSearchCriteria criteria) {
        return bookJpaRepository.searchBooks(
                criteria.title(),
                criteria.author(),
                criteria.minPrice(),
                criteria.maxPrice()
        ).stream()
        .map(Book::from)
        .toList();

    }

    private BookJpaEntity findActiveBookEntityById(Long id) {
        return bookJpaRepository.findActiveById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
    }
}
