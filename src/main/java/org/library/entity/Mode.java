package org.library.entity;

public enum Mode {
    APPLICATION(1,"일반 모드"),
    TEST(2, "테스트 모드");

    Mode(int num, String name) {
        this.num = num;
        this.name = name;
    }
    private int num;
    private String name;
}
