package com.programmers.util;

public enum Messages {
    SELECT_MODE("select.mode"),
    CONTINUE_PROMPT("continue.prompt"),
    INPUT_MISS("input.miss")
    ;

    private final String key;

    Messages(String key) {
        this.key = key;
    }

    public String getMessage() {
        return MessageProperties.get(key);
    }
}
