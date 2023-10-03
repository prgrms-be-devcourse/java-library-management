package constant;

import java.util.Arrays;

public enum Mode {
    NORMAL_MODE(1),
    TEST_MODE(2),
    ;

    private final int number;

    Mode(int number) {
        this.number = number;
    }

    public static Mode chosenMode(int modeNumber) {
        return Arrays.stream(Mode.values())
                .filter(mode -> mode.number == modeNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMsg.WRONG_MODE.getMessage()));
    }
}
