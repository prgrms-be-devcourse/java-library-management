package constant;

public enum Mode {
    NORMAL_MODE,
    TEST_MODE,
    WRONG_MODE;

    public static Mode chosenMode(int mode) {
        switch (mode) {
            case 1 -> {
                return NORMAL_MODE;
            }
            case 2 -> {
                return TEST_MODE;
            }
            default -> throw new IllegalArgumentException(ExceptionMsg.WRONG_MODE.getMessage());
        }
    }
}
