package com.programmers.config.enums;

import java.util.Arrays;
import java.util.List;

public enum ExitCommand {
    EXIT(Arrays.asList("y", "Y", "yes", "YES", "Yes"));

    private final List<String> commands;

    ExitCommand(List<String> commands) {
        this.commands = commands;
    }

    public static boolean isExit(String command) {
        return EXIT.commands.contains(command);
    }
}
