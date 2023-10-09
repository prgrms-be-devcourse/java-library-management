package com.programmers.app.mode;

import com.programmers.app.console.CommunicationAgent;

public enum Mode {
    NORMAL("일반 모드"),
    TEST("테스트 모드");

    private final String modeName;

    Mode(String modeName) {
        this.modeName = modeName;
    }

    public void printSelected(CommunicationAgent communicationAgent) {
        communicationAgent.printModeSelected(modeName);
    }

    public String displayName() {
        return this.modeName;
    }
}
