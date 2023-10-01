package org.example.domain;

public enum BookStatusType {
    BORROW_AVAILABE("대여 가능"),
    BORROWING("대여중"),
    ORGANIZING("도서 정리중"),
    LOST("분실됨");

    private String name;

    BookStatusType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static BookStatusType getValueByName(String name) {
        for (BookStatusType bookStatus : BookStatusType.values()) {
            if (name.equals(bookStatus.getName())) {
                return bookStatus;
            }
        }

        throw new IllegalArgumentException("유효하지 않은 도서 상태입니다.");
    }
}
