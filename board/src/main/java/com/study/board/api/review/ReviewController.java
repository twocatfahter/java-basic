package com.study.board.api.review;

import com.study.board.service.review.ReviewService;
import com.study.board.service.review.dto.ReviewServiceRequest;
import com.study.board.service.review.dto.ReviewServiceResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping("/{userId}")
    public ResponseEntity<ReviewServiceResponse> createReview(
            @PathVariable Long userId,
            @Valid @RequestBody ReviewServiceRequest request
            ) {
        ReviewServiceResponse review = reviewService.createReview(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    // 리뷰조회 (책 아이디기준)
    @GetMapping("/books/{bookId}")
    public ResponseEntity<Page<ReviewServiceResponse>> getReviewByBook(
            @PathVariable Long bookId,
            @PageableDefault(sort = "rating", direction = Sort.Direction.DESC) Pageable pageable
            ) {
        Page<ReviewServiceResponse> pageReviews = reviewService.getPageReviews(bookId, pageable);
        return ResponseEntity.ok(pageReviews);
    }

    // 리뷰조회 (책 아이디기준)
    @GetMapping("/query/books/{bookId}")
    public ResponseEntity<Page<ReviewServiceResponse>> getQueryReviewByBook(
            @PathVariable Long bookId,
            @PageableDefault(sort = "rating", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ReviewServiceResponse> pageReviews = reviewService.getQueryPageReviews(bookId, pageable);
        return ResponseEntity.ok(pageReviews);
    }


    // 도서별 평균 평점 조회
    @GetMapping("/books/{bookId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long bookId) {
        return ResponseEntity.ok(reviewService.calculateAverageRating(bookId));
    }
}

