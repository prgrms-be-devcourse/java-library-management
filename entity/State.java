package entity;

public enum State {
    AVAILABLE("대여 가능합니다."),
    RENT("이미 대여중인 도서입니다."),
    ORGANIZING("도서 정리중입니다."),
    LOST("분실된 도서입니다.");

    private final String description;

    State(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
