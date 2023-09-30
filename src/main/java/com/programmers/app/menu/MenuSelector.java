package com.programmers.app.menu;

import java.util.Arrays;

import com.programmers.app.console.CommunicationAgent;
import com.programmers.app.exception.InvalidInputException;

public class MenuSelector {
    private final CommunicationAgent communicationAgent;

    public MenuSelector(CommunicationAgent communicationAgent) {
        this.communicationAgent = communicationAgent;
    }

    public Menu select() {
        while (true) {
            try {
                int menuCode = communicationAgent.instructMenuSelection();
                return Arrays.stream(Menu.values())
                        .filter(menu -> menu.isSelected(menuCode))
                        .findFirst()
                        .orElseThrow(InvalidInputException::new);
            } catch (RuntimeException e) {
                //same as mode selector
                communicationAgent.askUserReEnter(e);
            }
        }
    }
}
