package domain;

public enum BookStatus {
    AVAILABLE("대여가능"), BORROWED("대여중"), ORGANIZING("정리중"), LOST("분실");

    private final String description;

    private BookStatus(String description) {
        this.description = description;
    }
    public String getDescription(){
        return description;
    }
}
