package com.programmers.librarymanagement.domain;

import java.time.LocalDateTime;

public class Book {

    private final Long id;

    private final String title;

    private final String author;

    private final int page;

    private Status status;

    private LocalDateTime returnDateTime;

    public Book(Long id, String title, String author, int page, Status status, LocalDateTime returnDateTime) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.page = page;
        this.status = status;
        this.returnDateTime = returnDateTime;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPage() {
        return page;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getReturnDateTime() {
        return returnDateTime;
    }

    public Boolean isSame(Long id) {
        return this.id.equals(id);
    }

    public Boolean isContain(String title) {
        return this.title.contains(title);
    }

    public void updateStatusToCanRent() {
        this.status = Status.CAN_RENT;
    }

    public void updateStatusToAlreadyRent() {
        this.status = Status.ALREADY_RENT;
    }

    public void updateStatusToArrange() {
        this.status = Status.ARRANGE;
    }

    public void updateStatusToLost() {
        this.status = Status.LOST;
    }

    public void updateReturnDateTime() {
        this.returnDateTime = LocalDateTime.now();
    }
}
