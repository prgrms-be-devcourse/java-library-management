package com.programmers.library.domain;

public enum BookStatus {
    AVAILABLE("대여 가능"),
    BORROWED("대여중"),
    ORGANIZING("도서 정리중"),
    LOST("분실됨");

    private final String name;

    BookStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
