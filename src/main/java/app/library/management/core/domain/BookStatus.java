package app.library.management.core.domain;

import java.util.function.Function;

/**
 * 도서 상태를 나타냄
 *
 * 반납 가능 - 대여중(RENTED)
 * 반납 불가능 - 대여 가능(AVAILABLE), 도서 정리중(ORGANIZING), 분실(LOST)
 */
public enum BookStatus {

    RENTED("대여중"),
    AVAILABLE("대여 가능"),
    ORGANIZING("정리중"),
    LOST("분실");

    BookStatus(String title) {
        this.title = title;
    }

    private String title;

    public String getTitle() {
        return title;
    }
}
