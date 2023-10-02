package com.programmers.library.domain;
public enum BookStatusType {
    RENTABLE("대여가능"),
    RENTED("대여중"),
    ORGANIZING("정리중"),
    LOST("분실");

    private final String description;

    BookStatusType(String status) {
        this.description = status;
    }
    public String getDescription(){
        return description;
    }
}
