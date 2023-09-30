package io;

public enum Mode {
    GENERAL(1),
    TEST(2);

    private final int type;

    Mode(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
