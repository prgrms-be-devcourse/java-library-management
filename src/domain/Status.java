package domain;

public enum Status {
    POSSIBLE("대여 가능"),
    IMPOSSIBLE("대여중"),
    ORGANIZE("도서 정리중"),
    LOST("분실됨");

    private final String statusName;

    Status(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName(){
        return statusName;
    }
}
