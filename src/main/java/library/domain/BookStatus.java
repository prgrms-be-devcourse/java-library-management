package library.domain;

public enum BookStatus {
    AVAILABLE("대여 가능"),
    RENTED("대여중"),
    IN_CLEANUP("도서 정리중"),
    LOST("분실됨");

    private final String description;

    BookStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
