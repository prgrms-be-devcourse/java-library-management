package devcourse.backend.business;

import java.util.Arrays;

public enum ModeType {
    NORMAL_MODE(1, "일반 모드"),
    TEST_MODE(2, "테스트 모드");

    private final int num;
    private final String description;

    ModeType(int num, String description) {
        this.num = num;
        this.description = description;
    }

    @Override
    public String toString() {
        return num + ". " + description;
    }

    public static ModeType getByNumber(int num) {
        return Arrays.stream(ModeType.values())
                .filter(e -> e.num == num)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 모드입니다."));
    }
}