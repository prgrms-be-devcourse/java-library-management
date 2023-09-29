package com.programmers.util;

public enum Messages {
    SELECT_MODE("select.mode"),
    Exit_PROMPT("exitConfirmation.prompt"),
    SELECT_MENU("select.menu"),
    Return_MENU("return.mainMenu"),


    ;

    private final String key;

    Messages(String key) {
        this.key = key;
    }

    public String getMessage() {
        return MessageProperties.get(key);
    }
}
