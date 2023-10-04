package com.libraryManagement.domain;

public enum BookStatus {

    POSSIBLERENT("대여가능"),
    NOPOSSIBLERENT("대여중"),
    READY("준비중"),
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