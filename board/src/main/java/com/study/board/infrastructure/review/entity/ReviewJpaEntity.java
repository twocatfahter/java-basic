package com.study.board.infrastructure.review.entity;

import com.study.board.domains.review.model.Review;
import com.study.board.global.common.BaseEntity;
import com.study.board.infrastructure.book.entity.BookJpaEntity;
import com.study.board.infrastructure.user.entity.UserJpaEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewJpaEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Min(1) @Max(5)
    @Column(name = "rating")
    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookJpaEntity book;

    public static ReviewJpaEntity fromDomain(Review review, UserJpaEntity user, BookJpaEntity book) {
        return ReviewJpaEntity.builder()
                .content(review.getContent())
                .rating(review.getRating())
                .user(user)                 // 연관관계 설정
                .book(book)
                .build();
    }
}
