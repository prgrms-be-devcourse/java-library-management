package domain;

public enum BookState {
    AVAILABLE("대여 가능" + System.lineSeparator()),
    RENTED("대여중" + System.lineSeparator()),
    PROCESSING("정리중" + System.lineSeparator()),
    LOST("분실됨" + System.lineSeparator()),
    DELETED("삭제됨" + System.lineSeparator());

    private final String displayName;
    BookState(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
