package com.study.board.service.review.dto;

public record ReviewServiceRequest(
        String content,
        int rating,
        Long bookId
) {
}
