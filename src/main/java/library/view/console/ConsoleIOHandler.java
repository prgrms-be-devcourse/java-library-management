package library.view.console;

import library.view.console.in.ConsoleInput;
import library.view.console.out.ConsoleOutput;

public class ConsoleIOHandler {

    private final ConsoleInput consoleInput;
    private final ConsoleOutput consoleOutput;

    public ConsoleIOHandler(ConsoleInput consoleInput, ConsoleOutput consoleOutput) {
        this.consoleInput = consoleInput;
        this.consoleOutput = consoleOutput;
    }
}
