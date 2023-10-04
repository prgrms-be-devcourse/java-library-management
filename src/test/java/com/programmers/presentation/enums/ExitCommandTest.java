package com.programmers.presentation.enums;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.programmers.exception.unchecked.InvalidExitCommandException;
import org.junit.jupiter.api.Test;

class ExitCommandTest {

    @Test
    void testIsExitCommand_ValidCommands() {
        assertTrue(ExitCommand.isExitCommand("y"));
        assertTrue(ExitCommand.isExitCommand("yes"));
        assertTrue(ExitCommand.isExitCommand("Y"));
        assertTrue(ExitCommand.isExitCommand("YES"));
    }

    @Test
    void testIsExitCommand_InvalidCommands() {
        assertFalse(ExitCommand.isExitCommand("n"));
        assertFalse(ExitCommand.isExitCommand("no"));
        assertFalse(ExitCommand.isExitCommand("maybe"));
    }

    @Test
    void testIsNotExitCommand_ValidCommands() {
        assertTrue(ExitCommand.isNotExitCommand("n"));
        assertTrue(ExitCommand.isNotExitCommand("no"));
        assertTrue(ExitCommand.isNotExitCommand("N"));
        assertTrue(ExitCommand.isNotExitCommand("NO"));
    }

    @Test
    void testIsNotExitCommand_InvalidCommands() {
        assertFalse(ExitCommand.isNotExitCommand("y"));
        assertFalse(ExitCommand.isNotExitCommand("yes"));
        assertFalse(ExitCommand.isNotExitCommand("maybe"));
    }

    @Test
    void testPromptForExit_NotExitCommand() {
        assertDoesNotThrow(() -> ExitCommand.promptForExit("n"));
    }

    @Test
    void testPromptForExit_InvalidCommand() {
        assertThrows(InvalidExitCommandException.class, () -> ExitCommand.promptForExit("maybe"));
    }
}
