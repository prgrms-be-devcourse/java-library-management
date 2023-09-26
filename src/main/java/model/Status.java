package model;

import java.util.Objects;

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

    public static Status findStatusByString(String statusString) {
        for (Status status : Status.values()) {
            if (status.getStatus().equals(statusString)) {
                return status;
            }
        }
        return null;
    }

    public static boolean isBorrowed(Status status) {
        return Objects.equals(BORROWED, status);
    }
    public static boolean isOrganizing(Status status) {
        return Objects.equals(ORGANIZING, status);
    }
    public static boolean isLost(Status status) {
        return Objects.equals(LOST, status);
    }
}
