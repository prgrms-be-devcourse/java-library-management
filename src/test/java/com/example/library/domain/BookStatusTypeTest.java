package com.example.library.domain;

import com.example.library.validation.BookStatusValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BookStatusTypeTest {

    @Test
    @DisplayName("대여가능한 도서는 대여에 성공한다.")
    public void successBorrowBook() {
        assertTrue(BookStatusValidator.borrowBook(BookStatusType.대여가능));

    }

    @Test
    @DisplayName("대여중,도서정리중,분실중인 도서는 대여에 실패한다.")
    public void failBorrowBook() {

        assertFalse(BookStatusValidator.borrowBook(BookStatusType.대여중));
        assertFalse(BookStatusValidator.borrowBook(BookStatusType.도서정리중));
        assertFalse(BookStatusValidator.borrowBook(BookStatusType.분실됨));

    }

    @Test
    @DisplayName("대여중이거나 분실중인 도서은 반납에 성공한다.")
    public void successReturnBook() {
        assertTrue(BookStatusValidator.returnBook(BookStatusType.대여중));
        assertTrue(BookStatusValidator.returnBook(BookStatusType.대여중));
        assertTrue(BookStatusValidator.returnBook(BookStatusType.분실됨));

    }

    @Test
    @DisplayName("이미 대여가능하거나 정리중인 도서은 반납에 실패한다.")
    public void failReturnBook() {
        assertFalse(BookStatusValidator.returnBook(BookStatusType.대여가능));
        assertFalse(BookStatusValidator.returnBook(BookStatusType.도서정리중));

    }

    @Test
    @DisplayName("대여가능, 대여중, 정리중인 도서는 분실 처리가 가능하다.")
    public void successLoseBook() {
        assertTrue(BookStatusValidator.loseBook(BookStatusType.대여가능));
        assertTrue(BookStatusValidator.loseBook(BookStatusType.대여중));
        assertTrue(BookStatusValidator.loseBook(BookStatusType.도서정리중));

    }

    @Test
    @DisplayName("이미 분실처리된 도서은 분실 처리에 실패한다.")
    public void failLoseBook() {
        assertFalse(BookStatusValidator.loseBook(BookStatusType.분실됨));
    }
}
