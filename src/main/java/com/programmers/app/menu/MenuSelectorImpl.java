package com.programmers.app.menu;

import com.programmers.app.console.CommunicationAgent;
import com.programmers.app.exception.InvalidInputException;

public class MenuSelectorImpl implements MenuSelector {
    private final CommunicationAgent communicationAgent;

    public MenuSelectorImpl(CommunicationAgent communicationAgent) {
        this.communicationAgent = communicationAgent;
    }

    @Override
    public Menu select() {
        while (true) {
            try {
                int menuCode = communicationAgent.instructMenuSelection();
                switch (menuCode) {
                    case 0:
                        return Menu.EXIT;
                    case 1:
                        return Menu.REGISTER;
                    case 2:
                        return Menu.REFERNCEALL;
                    case 3:
                        return Menu.SEARCHTITLE;
                    case 4:
                        return Menu.BORROW;
                    case 5:
                        return Menu.RETURN;
                    case 6:
                        return Menu.REPORTLOST;
                    case 7:
                        return Menu.DELETE;
                    default:
                        throw new InvalidInputException();
                }
            } catch (RuntimeException e) {
                //same as mode selector
                System.out.println(e.getMessage());
                System.out.println("다시 입력해주세요.");
            }
        }
    }
}
