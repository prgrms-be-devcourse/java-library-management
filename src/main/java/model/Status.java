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

    public static boolean isBorrowed(String statusString) {
        return Objects.equals(BORROWED, findStatusByString(statusString));
    }
    public static boolean isOrganizing(String statusString) {
        return Objects.equals(ORGANIZING, findStatusByString(statusString));
    }
    public static boolean isLost(String statusString) {
        return Objects.equals(LOST, findStatusByString(statusString));
    }
}
