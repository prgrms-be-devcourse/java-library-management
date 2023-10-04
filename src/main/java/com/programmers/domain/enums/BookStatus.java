package com.programmers.domain.enums;

import com.programmers.exception.unchecked.BookRentFailedException;
public enum BookStatus {
    AVAILABLE("대여 가능"),
    RENTED("대여 중"),
    ORGANIZING("정리 중"),
    LOST("분실");

    private final String name;

    BookStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static void checkIfRentable(BookStatus status) {
        if (status != AVAILABLE) {
            throw new BookRentFailedException();
        }
    }

}
