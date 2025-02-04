package com.study.board.domains.review.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class Review {
    private Long id;
    private String content;
    private int rating;
    private Long userId;
    private Long bookId;

    public void validateRating() {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("평점은 1~5점 사이여야 합니다.");
        }
    }
}
