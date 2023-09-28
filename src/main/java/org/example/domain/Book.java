package org.example.domain;

import java.time.Duration;
import java.time.Instant;

public class Book {
    private Integer id;
    private String title;
    private String author;
    private Integer pageSize;
    private BookStatus status;
    private Instant returnTime;

    public Book(Integer id, String title, String author, Integer pageSize) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pageSize = pageSize;
        this.status = BookStatus.BORROW_AVAILABE;
        this.returnTime = null;
    }

    public Book(Integer id, String title, String author, Integer pageSize, BookStatus status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pageSize = pageSize;
        this.status = status;
        this.returnTime = null;
    }

    public Book(Integer id, String title, String author, Integer pageSize, BookStatus status, Instant returnTime) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pageSize = pageSize;
        this.status = status;
        this.returnTime = returnTime;
    }

    @Override
    public String toString() {
        return "도서번호: " + id + "\n" +
                "제목: " + title + "\n" +
                "작가 이름: " + author + "\n" +
                "페이지 수: " + pageSize + "페이지" + "\n" +
                "상태: " + status.getKoreanName();
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public void setReturnTime(Instant returnTime) {
        this.returnTime = returnTime;
    }

    public Instant getReturnTime() {
        return returnTime;
    }

    public boolean canBorrow() {
        return status == BookStatus.BORROW_AVAILABE ||
                (status == BookStatus.ORGANIZING && Duration.between(returnTime, Instant.now()).toMinutes() >= 5);
    }

    public boolean canReturn() {
        return status == BookStatus.BORROWING || status == BookStatus.LOST;
    }

    public boolean canLost() {
        return status != BookStatus.LOST;
    }
}
