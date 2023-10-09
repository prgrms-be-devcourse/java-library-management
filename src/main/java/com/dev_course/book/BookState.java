package com.dev_course.book;

public enum BookState {
    AVAILABLE("대여 가능"),
    LOAN("대여 중"),
    PROCESSING("도서 정리 중"),
    LOST("분실");

    private final String label;

    BookState(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    public boolean isRentable() {
        return this == AVAILABLE;
    }

    public boolean isReturnable() {
        return this == LOAN || this == LOST;
    }
}
