package com.study.board.infrastructure.review.repository;

import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.board.infrastructure.review.entity.QReviewJpaEntity;
import com.study.board.infrastructure.user.entity.QUserJpaEntity;
import com.study.board.service.review.dto.ReviewServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepositoryImpl implements ReviewQueryRepository{
    private final JPAQueryFactory factory;

    @Override
    public Page<ReviewServiceResponse> queryFindReviewsByBookId(Long bookId, Pageable pageable) {
        QReviewJpaEntity review = QReviewJpaEntity.reviewJpaEntity;
        QUserJpaEntity user = QUserJpaEntity.userJpaEntity;

        List<ReviewServiceResponse> content = factory
                .select(Projections.constructor(ReviewServiceResponse.class,
                        review.id,
                        review.content,
                        review.rating,
                        user.username,
                        review.createdAt
                ))
                .from(review)
                .leftJoin(review.user, user)
                .where(
                        review.book.id.eq(bookId),
                        review.isDeleted.isFalse()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(review.createdAt.desc())
                .fetch();

        Long total = factory
                .select(review.count())
                .from(review)
                .where(
                        review.isDeleted.isFalse(),
                        review.book.id.eq(bookId)
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
}
