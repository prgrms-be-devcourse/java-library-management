package com.programmers.library.utils;

public enum ModeType {
    NORMAL(1, "일반 모드"),
    TEST(2, "테스트 모드");

    private int modeNum;        // 모드 번호
    private String modeName;    // 모드 이름
    private static ConsoleIO console = new ConsoleIO();

    ModeType(int modeNum, String modeName) {
        this.modeNum = modeNum;
        this.modeName = modeName;
    }

    private static ModeType getModeByNum(int modeNum) {
        for(ModeType mode : ModeType.values()) {
            if(mode.modeNum == modeNum) {
                console.printMessage("\n[System] " + mode.modeName + "로 애플리케이션을 실행합니다.\n");
                return mode;
            }
        }
        throw new IllegalArgumentException("모드를 찾을 수 없습니다");
    }

    public static ModeType selectMode() {
        console.printMessage("Q. 모드를 선택헤주세요.");
        for(ModeType mode : ModeType.values()) {
            console.printMessage(mode.modeNum + ". " + mode.modeName);
        }

        int modeInput = console.getIntInput("");
        return getModeByNum(modeInput);
    }

}