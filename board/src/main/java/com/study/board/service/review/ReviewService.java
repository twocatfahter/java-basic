package com.study.board.service.review;

import com.study.board.domains.review.model.Review;
import com.study.board.infrastructure.book.entity.BookJpaEntity;
import com.study.board.infrastructure.book.repository.BookJpaRepository;
import com.study.board.infrastructure.review.entity.ReviewJpaEntity;
import com.study.board.infrastructure.review.repository.ReviewJpaRepository;
import com.study.board.infrastructure.user.entity.UserJpaEntity;
import com.study.board.infrastructure.user.repository.UserJpaRepository;
import com.study.board.service.review.dto.ReviewServiceResponse;
import com.study.board.service.review.dto.ReviewServiceRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewJpaRepository reviewJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final BookJpaRepository bookJpaRepository;

    public ReviewServiceResponse createReview(ReviewServiceRequest request, Long userId) {
        UserJpaEntity user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        BookJpaEntity book = bookJpaRepository.findActiveById(request.bookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        Review review = Review.builder()
                .content(request.content())
                .rating(request.rating())
                .userId(user.getId())
                .bookId(book.getId())
                .build();

        review.validateRating();

        ReviewJpaEntity savedReview = reviewJpaRepository.save(
                ReviewJpaEntity.fromDomain(review, user, book)
        );

        return convertToResponse(savedReview, user.getUsername());

    }

    @Transactional(readOnly = true)
    public Page<ReviewServiceResponse> getPageReviews(Long bookId, Pageable pageable) {
        Page<ReviewJpaEntity> entities = reviewJpaRepository.findByBookIdAndIsDeletedFalse(bookId, pageable);

        return entities.map(entity -> convertToResponse(entity,
                entity.getUser().getUsername()));

    }

    @Transactional(readOnly = true)
    public double calculateAverageRating(Long bookId) {
        return reviewJpaRepository.findByBookIdAndIsDeletedFalse(bookId).stream()
                .mapToInt(ReviewJpaEntity::getRating)
                .average()
                .orElse(0.0);
    }


    private ReviewServiceResponse convertToResponse(ReviewJpaEntity savedReview, String username) {
        return new ReviewServiceResponse(
                savedReview.getId(),
                savedReview.getContent(),
                savedReview.getRating(),
                username,
                savedReview.getCreatedAt()
        );
    }

}
