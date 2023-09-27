package com.programmers.config.enums;

import com.programmers.exception.checked.InvalidExitCommandException;
import com.programmers.presentation.UserInteraction;
import com.programmers.util.Messages;

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

    public static void promptForExit(UserInteraction userInteraction) {
        try {
            userInteraction.displayMessage(Messages.CONTINUE_PROMPT.getMessage());
            String exitInput = userInteraction.collectUserInput();

            if (isExitCommand(exitInput)) {
                System.exit(0);
            } else if (isNotExitCommand(exitInput)) {
                return;
            }

            throw new InvalidExitCommandException(INVALID_EXIT);
        } catch (Exception e) {
            userInteraction.displayMessage(e.getMessage());
            promptForExit(userInteraction);
        }
    }
}
