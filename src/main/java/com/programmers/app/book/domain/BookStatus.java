package com.programmers.app.book.domain;

public enum BookStatus {
    IN_PLACE("대여 가능"),
    BORROWED("대여중"),
    LOST("분실중"),
    ON_ARRANGEMENT("도서 정리중");

    private final String status;

    BookStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
