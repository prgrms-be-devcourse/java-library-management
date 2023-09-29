package domain;

public enum BookState {
    RENTING("대여중"),
    AVAILABLE("대여 가능"),
    LOST("분실됨"),
    ORGANIZING("도서 정리중");

    BookState(String state) {
    }
}
