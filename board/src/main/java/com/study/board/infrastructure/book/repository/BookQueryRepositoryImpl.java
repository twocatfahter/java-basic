package com.study.board.infrastructure.book.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.board.domains.book.model.Book;
import com.study.board.infrastructure.book.entity.QBookJpaEntity;
import com.study.board.service.book.dto.BookSearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookQueryRepositoryImpl implements BookQueryRepository {
    private final JPAQueryFactory factory;

    @Override
    public List<Book> searchQueryBooks(BookSearchCriteria criteria) {
        QBookJpaEntity bookJpaEntity = QBookJpaEntity.bookJpaEntity;
        return factory.selectFrom(bookJpaEntity)
                .where(
                        isDeletedFalse(),
                        titleContains(criteria.title()),
                        authorContains(criteria.author()),
                        priceGoe(criteria.minPrice()),
                        priceLoe(criteria.maxPrice())
                )
                .fetch()
                .stream()
                .map(Book::from)
                .toList();
    }

    private BooleanExpression isDeletedFalse() {
        return QBookJpaEntity.bookJpaEntity.isDeleted.isFalse();
    }

    private BooleanExpression titleContains(String title) {
        return title != null ?
                QBookJpaEntity.bookJpaEntity.title.contains(title) : null;
    }

    private BooleanExpression authorContains(String author) {
        return author != null ?
                QBookJpaEntity.bookJpaEntity.author.contains(author) : null;
    }

    private BooleanExpression priceGoe(Integer minPrice) {
        return minPrice != null ?
                QBookJpaEntity.bookJpaEntity.price.goe(minPrice) : null;
    }

    private BooleanExpression priceLoe(Integer maxPrice) {
        return maxPrice != null ?
                QBookJpaEntity.bookJpaEntity.price.loe(maxPrice) : null;
    }
}
