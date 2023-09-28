package main.entity;

public enum State {
    AVAILABLE("대여 가능"),
    RENTED("대여중"),
    PROCESSING("정리중"),
    LOST("분실됨"),
    DELETED("삭제됨");

    private final String koreanState;
    State(String koreanState) {
        this.koreanState = koreanState;
    }

    public String getKoreanState() {
        return koreanState;
    }
}
