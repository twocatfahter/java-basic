package com.study.board.domains.book.model;

import com.study.board.infrastructure.book.entity.BookJpaEntity;
import lombok.Builder;
import lombok.Getter;

/**
 *  도메인 객체(순수 자바 객체), 엔티티 객체 (데이터베이스 구조와 같다 하나의 테이블과 데이터상 같다)
 *
 */

@Getter
@Builder
public class Book {
    private final Long id;
    private String title;
    private String author;
    private String isbn;
    private int price;
    private int stockQuantity;
    private boolean isDeleted;

    private Book(Long id, String title,
                 String author, String isbn,
                 int price, int stockQuantity,
                 boolean isDeleted) {

        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.isDeleted = isDeleted;
    }

    // 도메인 로직
    public void updateStock(int quantity) {
        int newQuantity = this.stockQuantity + quantity;
        if (newQuantity < 0) {
            throw new IllegalArgumentException("재고가 부족합니다, 현재 재고: " + this.stockQuantity);
        }
        this.stockQuantity = newQuantity;
    }

    // 정적 팩토리 메서드
    public static Book create(String title, String author, String isbn, int price, int stockQuantity) {
        return Book.builder()
                .title(title)
                .author(author)
                .isbn(isbn)
                .price(price)
                .stockQuantity(stockQuantity)
                .build();
    }

    public static Book from(BookJpaEntity entity) {
        return Book.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .isbn(entity.getIsbn())
                .price(entity.getPrice())
                .stockQuantity(entity.getStockQuantity())
                .build();
    }
}
