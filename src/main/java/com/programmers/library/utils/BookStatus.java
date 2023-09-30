package com.programmers.library.utils;

public enum BookStatus {
    AVAILABLE("대여 가능"),
    RENTING("대여중"),
    ORGANIZING("도서 정리중"),
    LOST("분실됨");

    private String description;

    public String getDescription() {
        return description;
    }

    BookStatus(String description) {
        this.description = description;
    }
}
