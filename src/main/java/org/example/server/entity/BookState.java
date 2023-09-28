package org.example.server.entity;

public enum BookState {
    CAN_BORROW("대여 가능"),
    BORROWED("대여중"),
    LOADING("도서 정리중"),
    LOST("분실됨");
    private final String status;

    BookState(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
