package com.study.board.infrastructure.book.entity;

import com.study.board.domains.book.model.Book;
import com.study.board.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
@Builder
@Getter
@AllArgsConstructor
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryJpaEntity category;

    public static BookJpaEntity from(Book book) {
        BookJpaEntity entity = new BookJpaEntity();
        entity.title = book.getTitle();
        entity.author = book.getAuthor();
        entity.isbn = book.getIsbn();
        entity.price = book.getPrice();
        entity.stockQuantity = book.getStockQuantity();
        return entity;
    }

    // 엔티티 수정 메서드
    public void update(Book book) {
        this.price = book.getPrice();
        this.stockQuantity = book.getStockQuantity();
    }
}
