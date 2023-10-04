package com.programmers.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Book {

    private int id;
    private String name;
    private String author;
    private int pageCount;
    private BookStatus status;
    private LocalDateTime returnedAt;

    public Book() {
    }

    public Book(int id, String name, String author, int pageCount, BookStatus status) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.pageCount = pageCount;
        this.status = status;
    }

    public Book(int id, String name, String author, int pageCount, BookStatus status, LocalDateTime returnedAt) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.pageCount = pageCount;
        this.status = status;
        this.returnedAt = returnedAt;
    }

    public boolean isIdEqualTo(int id) {
        return this.id == id;
    }

    public boolean isNameContains(String name) {
        return this.name.contains(name);
    }

    public void borrowed() {
        if (status == BookStatus.BORROWED) {
            throw new IllegalStateException("[System] 이미 대여중인 도서입니다.");
        }

        if (status == BookStatus.LOST) {
            throw new IllegalStateException("[System] 분실된 도서입니다.");
        }

        if (!isBorrowable()) {
            throw new IllegalStateException("[System] 정리중인 도서입니다.");
        }

        status = BookStatus.BORROWED;
    }

    public void returned() {
        if (status == BookStatus.BORROWABLE) {
            throw new IllegalStateException("[System] 원래 대여가 가능한 도서입니다.");
        }

        if (status == BookStatus.ORGANIZING && !isBorrowable()) {
            throw new IllegalStateException("[System] 이미 반납된 도서입니다.");
        }

        returnedAt = LocalDateTime.now();
        status = BookStatus.ORGANIZING;
    }

    public void lost() {
        if (status == BookStatus.LOST) {
            throw new IllegalStateException("[System] 이미 분실 처리된 도서입니다.");
        }

        status = BookStatus.LOST;
    }

    public void borrowable() {
        status = BookStatus.BORROWABLE;
    }

    @JsonIgnore
    public boolean isBorrowable() {
        if (status == BookStatus.BORROWABLE) {
            return true;
        }

        if (status == BookStatus.ORGANIZING) {
            long minutesElapsed = Duration.between(returnedAt, LocalDateTime.now()).toMinutes();
            return minutesElapsed > 5;
        }

        return false;
    }

    public int getId() {
        return id;
    }

    //! 테스트용 Getter

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getPageCount() {
        return pageCount;
    }

    public BookStatus getStatus() {
        return status;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    @Override
    public String toString() {
        return "도서번호 : " + id + '\n' +
                "제목 : " + name + '\n' +
                "작가 이름 : " + author + '\n' +
                "페이지 수 : " + pageCount + '\n' +
                "상태 : " + status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && pageCount == book.pageCount && Objects.equals(name, book.name) && Objects.equals(author, book.author) && status == book.status && Objects.equals(returnedAt, book.returnedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author, pageCount, status, returnedAt);
    }
}
