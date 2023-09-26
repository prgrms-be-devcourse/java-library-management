package com.programmers.librarymanagement.domain;

public enum Status {

    CAN_RENT("canRent"), // 대여 가능
    CANNOT_RENT("cannotRent"), // 대여중
    ARRANGE("arrange"), // 도서 정리중
    LOST("lost"); // 분실됨

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
