package com.study.board.infrastructure.book.entity;

import com.study.board.domains.book.model.Category;
import com.study.board.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "category")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CategoryJpaEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;

    public static CategoryJpaEntity from(Category category) {
        return CategoryJpaEntity.builder()
                .name(category.getName())
                .code(category.getCode())
                .build();
    }

    public Category of() {
        return Category.create(
                name,
                code
        );
    }
}
