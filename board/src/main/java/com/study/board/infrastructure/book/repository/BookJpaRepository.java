package com.study.board.infrastructure.book.repository;

import com.study.board.infrastructure.book.entity.BookJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookJpaRepository extends JpaRepository<BookJpaEntity, Long> {

    @Query("SELECT b FROM BookJpaEntity b WHERE b.id = :id AND b.isDeleted = false")
    Optional<BookJpaEntity> findActiveById(@Param("id") Long id);
}
