package com.programmers.config.enums;

import com.programmers.exception.checked.InvalidExitCommandException;

import java.util.Arrays;
import java.util.List;

import static com.programmers.exception.ErrorCode.INVALID_EXIT;

public enum ExitCommand {
    EXIT(Arrays.asList("y", "Y", "yes", "YES", "Yes")),
    NOT_EXIT(Arrays.asList("n", "N", "no", "NO", "No"));

    private final List<String> commands;

    ExitCommand(List<String> commands) {
        this.commands = commands;
    }

    public static boolean isExitCommand(String command) {
        return EXIT.commands.contains(command);
    }

    public static boolean isNotExitCommand(String command) {
        return NOT_EXIT.commands.contains(command);
    }

    //TODO: 종료 시 메세지를 보내려면?
    public static void promptForExit(String exitInput) throws InvalidExitCommandException {
        if (isExitCommand(exitInput)) {
            System.exit(0);
        } else if (isNotExitCommand(exitInput)) {
            return;
        }
        throw new InvalidExitCommandException(INVALID_EXIT);
    }
}
