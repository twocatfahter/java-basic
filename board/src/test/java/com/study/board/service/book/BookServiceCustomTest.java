package com.study.board.service.book;

import com.study.board.domains.book.model.Book;
import com.study.board.infrastructure.book.entity.BookJpaEntity;
import com.study.board.infrastructure.book.repository.BookJpaRepository;
import com.study.board.service.book.dto.BookServiceRequest;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceCustomTest {

    @Mock
    private BookJpaRepository bookJpaRepository;

    @InjectMocks
    private BookServiceCustom bookServiceCustom;

    private BookServiceRequest validRequest;
    private BookJpaEntity sampleBookEntity;

    @BeforeEach
    void setUp() {
        validRequest = new BookServiceRequest(
                "Sample title",
                "Sample author",
                "1234567890",
                10000,
                100
        );

        sampleBookEntity = BookJpaEntity.builder()
                .id(1L)
                .title("Sample title")
                .author("Sample author")
                .isbn("1234567890")
                .price(10000)
                .stockQuantity(100)
                .build();
    }

    @Test
    @DisplayName("책 생성 성공 테스트")
    void createBook_Success() {
        // given
//        when(bookJpaRepository.existsById(anyLong())).thenReturn(false);
        when(bookJpaRepository.save(any())).thenReturn(sampleBookEntity);

        // when
        Book result = bookServiceCustom.saveBook(validRequest);
        // then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(validRequest.title());
        verify(bookJpaRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("재고 업데이트 성공 테스트")
    void updateStock_Success() {
        // given
        Long bookId = 1L;
        int quantity = 50;

        when(bookJpaRepository.findActiveById(bookId)).thenReturn(Optional.of(sampleBookEntity));

        // when
        Book result = bookServiceCustom.updateStock(bookId, quantity);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getStockQuantity()).isEqualTo(150);
        verify(bookJpaRepository, times(1)).findActiveById(bookId);
    }

    @Test
    @DisplayName("존재하지 않는 책을 조회 시 예외 발생")
    void getBookById_NotFound_ThrowsException() {
        // given
        Long bookId = 999L;
        when(bookJpaRepository.findActiveById(bookId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> bookServiceCustom.getBookById(bookId))
                .isInstanceOf(EntityNotFoundException.class);
    }
}