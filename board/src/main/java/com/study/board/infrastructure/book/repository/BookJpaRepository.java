package com.study.board.infrastructure.book.repository;

import com.study.board.infrastructure.book.entity.BookJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookJpaRepository extends JpaRepository<BookJpaEntity, Long> {
}
