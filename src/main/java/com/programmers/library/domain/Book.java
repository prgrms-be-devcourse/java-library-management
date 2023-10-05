package com.programmers.library.domain;

import com.programmers.library.utils.StatusType;

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

    public Book(int bookId, String title, String author, int pages, StatusType status) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.status = status;
    }

    @Override
    public String toString() {
        return "도서번호 : " + bookId +
                "\n제목 : " + title +
                "\n작가 이름 : " + author +
                "\n페이지 수 : " + pages +
                "\n상태 : " + status.getDescription();
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPages() {
        return pages;
    }

    public StatusType getStatus() {
        return status;
    }

    // Setter 메서드 대신 새로운 객체 반환
    public Book updateStatus(StatusType newStatus) {
        return new Book(bookId, title, author, pages, newStatus);
    }
}