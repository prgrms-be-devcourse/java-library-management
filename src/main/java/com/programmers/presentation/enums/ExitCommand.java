package com.programmers.presentation.enums;

import com.programmers.exception.unchecked.InvalidExitCommandException;

import java.util.Arrays;
import java.util.List;

public enum ExitCommand {
    EXIT(Arrays.asList("y", "yes")),
    NOT_EXIT(Arrays.asList("n", "no"));

    private final List<String> commands;

    ExitCommand(List<String> commands) {
        this.commands = commands;
    }

    public static boolean isExitCommand(String command) {
        return EXIT.commands.contains(command.toLowerCase());
    }

    public static boolean isNotExitCommand(String command) {
        return NOT_EXIT.commands.contains(command.toLowerCase());
    }

    //TODO: 종료 시 메세지를 보내려면?
    public static void promptForExit(String exitInput) {
        if (isExitCommand(exitInput)) {
            System.exit(0);
        } else if (isNotExitCommand(exitInput)) {
            return;
        }
        throw new InvalidExitCommandException();
    }
}
