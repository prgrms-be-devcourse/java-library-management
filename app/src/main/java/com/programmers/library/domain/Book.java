package com.programmers.library.domain;

import java.time.Duration;
import java.time.LocalDateTime;

public class Book {

    private int id;
    private String name;
    private String author;
    private int pageCount;
    private BookStatus status;
    private LocalDateTime returnedAt;

    public Book(int id, String name, String author, int pageCount, BookStatus status) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.pageCount = pageCount;
        this.status = status;
    }

    public boolean isIdEqualTo(int id) {
        return this.id == id;
    }

    public boolean isNameContains(String name) {
        return name.contains(name);
    }

    public void borrowed() {
        if (status == BookStatus.BORROWED) {
            throw new IllegalStateException("[System] 이미 대여중인 도서입니다.");
        }

        if (status == BookStatus.LOST) {
            throw new IllegalStateException("[System] 분실된 도서입니다.");
        }

        if (!isOverFiveMinutesSinceReturned()) {
            throw new IllegalStateException("[System] 정리중인 도서입니다.");
        }

        this.status = BookStatus.BORROWED;
    }

    public void returned() {
        if (status == BookStatus.AVAILABLE) {
            throw new IllegalStateException("[System] 원래 대여가 가능한 도서입니다.");
        }

        if (status == BookStatus.AVAILABLE || !isOverFiveMinutesSinceReturned()) {
            throw new IllegalStateException("[System] 이미 반납된 도서입니다.");
        }

        this.returnedAt = LocalDateTime.now();
        this.status = BookStatus.ORGANIZING;
    }

    public void lost() {
        if (status == BookStatus.LOST) {
            throw new IllegalStateException("[System] 이미 분실 처리된 도서입니다.");
        }

        this.status = BookStatus.LOST;
    }

    public boolean isOverFiveMinutesSinceReturned() {
        if (returnedAt == null) {
            return true;
        }

        long minutesElapsed = Duration.between(returnedAt, LocalDateTime.now()).toMinutes();
        return minutesElapsed > 5;
    }

    @Override
    public String toString() {
        return "도서번호 : " + id + '\n' +
                "제목 : " + name + '\n' +
                "작가 이름 : " + author + '\n' +
                "페이지 수 : " + pageCount + '\n' +
                "상태 : " + status;
    }

}
