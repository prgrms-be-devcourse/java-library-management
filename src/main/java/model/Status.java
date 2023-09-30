package model;

import java.util.Arrays;

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
        return Arrays.stream(Status.values())
                .filter(status -> status.getStatus().equals(statusString))
                .findFirst()
                .orElseThrow(() -> new NullPointerException("정확한 상태를 입력해주세요"));
    }
}
