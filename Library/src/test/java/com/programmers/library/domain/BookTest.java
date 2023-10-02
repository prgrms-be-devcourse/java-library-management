package com.programmers.library.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTest {

    @Test
    @DisplayName("책 엔티티 생성 테스트")
    public void bookEntityTest(){
        // Given
        Long bookId = 1L;
        String title = "제목1";
        String author = "작가1";
        Integer page = 100;

        // When
        Book book = new Book(bookId, title, author, page);

        // Then
        assertEquals(book.getTitle(), title);
    }

    @Test
    @DisplayName("책 엔티티 상태 확인 테스트")
    public void bookStatusTest(){
        // Given
        Long bookId = 1L;
        String title = "제목1";
        String author = "작가1";
        Integer page = 100;
        BookStatusType status = BookStatusType.LOST;

        // When
        Book book = new Book(bookId, title, author, page, status);

        // Then
        BookStatusType bookStatus = book.getBookStatus();
        assertEquals(bookStatus.getDescription(), "분실");
    }
}
