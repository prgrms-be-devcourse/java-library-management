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

    //TODO: 종료 시 메세지를 보내려면? -> 다른 곳에서 보내서 처리
    public static boolean promptForExit(String exitInput) {
        if (isExitCommand(exitInput)) {
            return true;
        } else if (isNotExitCommand(exitInput)) {
            return false;
        }
        throw new InvalidExitCommandException();
    }
}
