package com.programmers.library.domain;
public enum BookStatus {
    RENTABLE("대여가능"),
    RENTED("대여중"),
    ORGANIZING("정리중"),
    LOST("분실");

    private final String statusDescription;

    BookStatus(String status) {
        this.statusDescription = status;
    }
    public String getStatusDescription(){
        return statusDescription;
    }
}
