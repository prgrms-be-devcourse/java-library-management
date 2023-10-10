package domain;

public enum BookCondition {
    AVAILABLE("대여 가능"),
    RENTED("대여 중"),
    ORGANIZING("도서 정리중"),
    LOST("분실됨");

    private final String condition;

    BookCondition(String condition) {
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

}
