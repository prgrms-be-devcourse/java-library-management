package com.example.library.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class BookStatusTest {

    @Test
    @DisplayName("대여가능한 도서는 대여에 성공한다.")
    public void successBorrowBook() {
        assertThat(BookStatus.대여가능.borrowBook()).isTrue();
    }

    @Test
    @DisplayName("대여중,도서정리중,분실중인 도서는 대여에 실패한다.")
    public void failBorrowBook() {

        assertThat(BookStatus.대여중.borrowBook()).isFalse();
        assertThat(BookStatus.도서정리중.borrowBook()).isFalse();
        assertThat(BookStatus.분실됨.borrowBook()).isFalse();
    }

    @Test
    @DisplayName("대여중이거나 분실중인 도서은 반납에 성공한다.")
    public void successReturnBook() {
        assertThat(BookStatus.대여중.returnBook()).isTrue();
        assertThat(BookStatus.분실됨.returnBook()).isTrue();
    }

    @Test
    @DisplayName("이미 대여가능하거나 정리중인 도서은 반납에 실패한다.")
    public void failReturnBook() {
        assertThat(BookStatus.대여가능.returnBook()).isFalse();
        assertThat(BookStatus.도서정리중.returnBook()).isFalse();
    }

    @Test
    @DisplayName("대여가능, 대여중, 정리중인 도서는 분실 처리가 가능하다.")
    public void successLoseBook() {
        assertThat(BookStatus.대여중.loseBook()).isTrue();
        assertThat(BookStatus.대여가능.loseBook()).isTrue();
        assertThat(BookStatus.도서정리중.loseBook()).isTrue();
    }

    @DisplayName("이미 분실처리된 도서은 분실 처리에 실패한다.")
    public void failLoseBook() {
        assertThat(BookStatus.분실됨.loseBook()).isFalse();
    }
}
