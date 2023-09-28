package org.example.domain;

public enum BookStatus {
    BORROW_AVAILABE("대여 가능"),
    BORROWING("대여중"),
    ORGANIZING("도서 정리중"),
    LOST("분실됨");

    private String koreanName;

    BookStatus(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static BookStatus getValueByKoreanName(String koreanName) {
        for (BookStatus bookStatus : BookStatus.values()) {
            if (koreanName.equals(bookStatus.getKoreanName())) {
                return bookStatus;
            }
        }

        return null;
    }
}
