package app.library.management.infra.mode;

public enum ExecutionMode {
    GENERAL(1), TEST(2);

    public static ExecutionMode fromNum(int num) {
        if (num == 1) return ExecutionMode.GENERAL;
        return ExecutionMode.TEST;
    }

    private int num;

    ExecutionMode(int num) {
        this.num = num;
    }

}
