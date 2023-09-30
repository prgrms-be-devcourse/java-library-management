package com.programmers.library.utils;

import java.util.Scanner;

public enum Mode {
    NORMAL(1, "일반 모드"),
    TEST(2, "테스트 모드");

    private int modeNum;        // 모드 번호
    private String modeName;    // 모드 이름

    Mode(int modeNum, String modeName) {
        this.modeNum = modeNum;
        this.modeName = modeName;
    }

    private static Mode getModeByNum(int modeNum) {
        for(Mode mode : Mode.values()) {
            if(mode.modeNum == modeNum) {
                System.out.println("\n[System] " + mode.modeName + "로 애플리케이션을 실행합니다.\n");
                return mode;
            }
        }
        throw new IllegalArgumentException("모드를 찾을 수 없습니다");
    }

    public static Mode selectMode() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Q. 모드를 선택헤주세요.");
        for(Mode mode : Mode.values()) {
            System.out.println(mode.modeNum + ". " + mode.modeName);
        }
        System.out.print("\n> ");

        int modeInput = scanner.nextInt();
        return getModeByNum(modeInput);
    }

}