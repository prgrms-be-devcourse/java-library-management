package library.view.console;

import library.view.console.in.ConsoleInput;
import library.view.console.out.ConsoleOutput;

public class ConsoleIOHandler {

    private static final String SYSTEM_MESSAGE_PREFIX = "[SYSTEM] ";
    private final ConsoleInput consoleInput;
    private final ConsoleOutput consoleOutput;

    public ConsoleIOHandler(ConsoleInput consoleInput, ConsoleOutput consoleOutput) {
        this.consoleInput = consoleInput;
        this.consoleOutput = consoleOutput;
    }

    public void printSystemMessage(String message) {
        consoleOutput.println(SYSTEM_MESSAGE_PREFIX + message);
        consoleOutput.printEmptyLine();
    }
}
