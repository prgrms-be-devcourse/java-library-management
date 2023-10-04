package com.programmers.library_management.domain;

public enum StatusType {
    Available("대여 가능"), Ranted("대여중"), Lost("분실"), Organize("정리중");
    private final String message;

    StatusType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
