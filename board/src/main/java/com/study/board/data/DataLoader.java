package com.study.board.data;

import com.study.board.infrastructure.book.entity.CategoryJpaEntity;
import com.study.board.infrastructure.book.repository.CategoryJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final CategoryJpaRepository categoryJpaRepository;

    @Autowired
    public DataLoader(CategoryJpaRepository categoryJpaRepository) {
        this.categoryJpaRepository = categoryJpaRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        if (categoryJpaRepository.count() == 0) {
            createCategories();
        }
    }

    private void createCategories() {
        CategoryJpaEntity novel = CategoryJpaEntity.builder()
                .name("소설")
                .code("NOVEL")
                .build();

        CategoryJpaEntity education = CategoryJpaEntity.builder()
                .name("교육")
                .code("EDUCATION")
                .build();

        CategoryJpaEntity computer = CategoryJpaEntity
                .builder()
                .name("컴퓨터/IT")
                .code("COMPUTER")
                .build();

        CategoryJpaEntity science = CategoryJpaEntity.builder()
                .name("과학")
                .code("SCIENCE")
                .build();

        categoryJpaRepository.saveAll(List.of(novel, education, computer, science));
    }
}
