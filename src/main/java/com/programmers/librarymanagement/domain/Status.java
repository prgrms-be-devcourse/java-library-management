package com.programmers.librarymanagement.domain;

public enum Status {

    CAN_RENT("대여 가능"), // 대여 가능
    ALREADY_RENT("대여중"), // 대여중
    ARRANGE("도서 정리중"), // 도서 정리중
    LOST("분실됨"); // 분실됨

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
