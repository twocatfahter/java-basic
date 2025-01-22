package com.study.board.infrastructure.book.entity;

import com.study.board.domains.book.model.Book;
import com.study.board.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stockQuantity;

    public static BookJpaEntity from(Book book) {
        BookJpaEntity entity = new BookJpaEntity();
        entity.title = book.getTitle();
        entity.author = book.getAuthor();
        entity.isbn = book.getIsbn();
        entity.price = book.getPrice();
        entity.stockQuantity = book.getStockQuantity();
        return entity;
    }
}
