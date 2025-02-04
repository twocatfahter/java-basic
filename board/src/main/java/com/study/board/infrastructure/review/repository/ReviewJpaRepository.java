package com.study.board.infrastructure.review.repository;

import com.study.board.infrastructure.book.entity.BookJpaEntity;
import com.study.board.infrastructure.review.entity.ReviewJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewJpaRepository extends JpaRepository<ReviewJpaEntity, Long> {

    @EntityGraph(attributePaths = {"user", "book"})
    Page<ReviewJpaEntity> findByBookIdAndIsDeletedFalse(Long bookId, Pageable pageable);

    List<ReviewJpaEntity> findByBookIdAndIsDeletedFalse(Long bookId);

    Long book(BookJpaEntity book);
}
