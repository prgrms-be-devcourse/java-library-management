package com.libraryManagement.domain;

public enum BookStatus {

    AVAILABLE("대여가능"),
    RENT("대여중"),
    ORGANIZING("정리중"),
    LOST("분실됨"),
    DELETE("삭제됨");

    final private String name;

    public String getName() {
        return name;
    }

    BookStatus(String name) {
        this.name = name;
    }
}
