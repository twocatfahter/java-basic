package com.study.board.infrastructure.review.repository;

import com.study.board.service.review.dto.ReviewServiceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewQueryRepository {
    Page<ReviewServiceResponse> queryFindReviewsByBookId(Long bookId, Pageable pageable);
}
