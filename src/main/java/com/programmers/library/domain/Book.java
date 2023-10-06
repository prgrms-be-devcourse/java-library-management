package com.programmers.library.domain;

import com.programmers.library.utils.StatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    private int bookId;
    private String title;
    private String author;
    private int pages;
    private StatusType status;

    public Book(String title, String author, int pages) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.status = StatusType.AVAILABLE; // 기본 도서 상태 - AVAILABLE(대여 가능)
    }

    // Setter 메서드 대신 새로운 객체 반환
    public Book updateStatus(StatusType newStatus) {
        return Book.builder()
                .bookId(this.bookId)
                .title(this.title)
                .author(this.author)
                .pages(this.pages)
                .status(newStatus)
                .build();
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "도서번호 : " + bookId +
                "\n제목 : " + title +
                "\n작가 이름 : " + author +
                "\n페이지 수 : " + pages +
                "\n상태 : " + status.getDescription();
    }
}