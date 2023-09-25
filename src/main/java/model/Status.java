package model;

public enum Status {
    AVAILABLE("대여 가능"),
    BORROWED("대여중"),
    ORGANIZING("도서 정리중"),
    LOST("분실됨"),
    ;

    private final String status;

    Status(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
