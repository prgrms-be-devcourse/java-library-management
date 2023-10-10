package org.library.entity;

public enum State {
    AVAILABLE("대여 가능"),
    RENT("대여중"),
    ORGANIZING("도서 정리중"),
    LOST("분실됨");

    private final String description;

    State(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
