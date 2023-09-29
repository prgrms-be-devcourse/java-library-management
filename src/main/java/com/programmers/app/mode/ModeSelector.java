package com.programmers.app.mode;

import com.programmers.app.console.CommunicationAgent;
import com.programmers.app.exception.InvalidInputException;

public class ModeSelector {
    private final CommunicationAgent communicationAgent;

    public ModeSelector(CommunicationAgent communicationAgent) {
        this.communicationAgent = communicationAgent;
    }

    public Mode select() {
        while (true) {
            try {
                int modeCode = communicationAgent.instructModeSelection();
                if (modeCode == 1) return Mode.NORMAL;
                else if (modeCode == 2) return Mode.TEST;
                else throw new InvalidInputException();
            } catch (InvalidInputException e) {
                //would like to manage this with another object
                System.out.println(e.getMessage());
                System.out.println("다시 입력해주세요.");
            }
        }
    }
}
