package controller;

import java.util.ArrayList;
import java.util.Arrays;

public enum ModeType {
    NORMAL("1", "일반 모드로 애플리케이션을 실행합니다."),
    TEST("2", "테스트 모드로 애플리케이션을 실행합니다.");

    private final String number;
    private final String message;

    public String getNumber() {
        return number;
    }

    public String getMessage() {
        return message;
    }

    ModeType(String number, String message) {
        this.number = number;
        this.message = message;
    }

    public static ModeType findByMode(String mode) {
        return Arrays.stream(ModeType.values())
                .filter(modeType -> modeType.getNumber().equals(mode))
                .findFirst()
                .orElse(null);
    }

}
