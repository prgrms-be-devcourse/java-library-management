package com.programmers.library.utils;

public enum StatusType {
    AVAILABLE("대여 가능"),
    RENTING("대여중"),
    ORGANIZING("도서 정리중"),
    LOST("분실됨");

    private String description;

    public String getDescription() {
        return description;
    }

    public static StatusType getStatus(String description) {    // description -> 열거형 상수
        for(StatusType status : StatusType.values()) {
            if(status.getDescription().equals(description)) {
                return status;
            }
        }

        throw new IllegalArgumentException("유효하지 않은 도서 상태입니다.");
    }

    StatusType(String description) {
        this.description = description;
    }
}
