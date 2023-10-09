package domain;

public enum BookStatus {
    BORROW_AVAILABLE("대여 가능"),
    BORROWED("대여중"),
    CLEANING("정리중"),
    LOST("분실");
    private final String label;

    BookStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
