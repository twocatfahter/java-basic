package com.study.board.service.book;

import com.study.board.domains.book.model.Book;
import com.study.board.global.exception.DuplicateIsbnException;
import com.study.board.infrastructure.book.entity.BookJpaEntity;
import com.study.board.infrastructure.book.entity.CategoryJpaEntity;
import com.study.board.infrastructure.book.repository.BookJpaRepository;
import com.study.board.infrastructure.book.repository.CategoryJpaRepository;
import com.study.board.service.book.dto.BookSearchCriteria;
import com.study.board.service.book.dto.BookServiceRequest;
import com.study.board.service.book.dto.BookServiceWithCategoryRequest;
import com.study.board.service.book.dto.BookUpdateServiceRequest;
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
    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    @Transactional
    public Book saveBook(BookServiceRequest request) {
        validateIsbnUnique(request.isbn());
        Book book = Book.create(
                request.title(),
                request.author(),
                request.isbn(),
                request.price(),
                request.stockQuantity(),
                null
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

    @Override
    public Book updateBookDetails(Long id, BookUpdateServiceRequest serviceRequest) {
        BookJpaEntity entity = findActiveBookEntityById(id);

        Book book = Book.from(entity);
        if (serviceRequest.price() != null) {
            book.updatePrice(serviceRequest.price());
        }

        if (serviceRequest.stockQuantity() != null) {
            book.updateStock(serviceRequest.stockQuantity());
        }

        entity.update(book);
        return Book.from(entity);
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return bookJpaRepository.findByAuthor(author).stream()
                .map(Book::from)
                .toList();

    }

    @Override
    @Transactional
    public Book createBookWithCategory(BookServiceWithCategoryRequest serviceRequest, Long categoryId) {
        validateIsbnUnique(serviceRequest.isbn());
        CategoryJpaEntity categoryEntity = categoryJpaRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        BookJpaEntity entity = BookJpaEntity.builder()
                .title(serviceRequest.title())
                .author(serviceRequest.author())
                .isbn(serviceRequest.isbn())
                .price(serviceRequest.price())
                .stockQuantity(serviceRequest.stockQuantity())
                .category(categoryEntity)
                .build();

        BookJpaEntity savedEntity = bookJpaRepository.save(entity);
        return Book.from(savedEntity);
    }

    private BookJpaEntity findActiveBookEntityById(Long id) {
        return bookJpaRepository.findActiveById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
    }

    private void validateIsbnUnique(String isbn) {
        if (bookJpaRepository.existsByIsbnAndIsDeletedFalse(isbn)) {
            throw new DuplicateIsbnException("ISBN already exists: " + isbn);
        }
    }
}
