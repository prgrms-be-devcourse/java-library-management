package library.view.console;

import library.dto.BookSaveRequest;
import library.exception.InputException;
import library.view.console.in.ConsoleInput;
import library.view.console.out.ConsoleOutput;
import library.view.constant.InputMessage;

import java.util.List;
import java.util.function.Function;

import static library.exception.InputErrorMessage.NOT_NUMBER;
import static library.view.constant.InputMessage.*;

public class ConsoleIOHandler {

    private static final String SYSTEM_MESSAGE_PREFIX = "[SYSTEM] ";
    private static final String QUESTION_MESSAGE_PREFIX = "Q. ";
    private static final String INPUT_PREFIX = "> ";
    private static final String SEPARATOR = "------------------------------";

    private final ConsoleInput consoleInput;
    private final ConsoleOutput consoleOutput;

    public ConsoleIOHandler(ConsoleInput consoleInput, ConsoleOutput consoleOutput) {
        this.consoleInput = consoleInput;
        this.consoleOutput = consoleOutput;
    }


    // Input
    public String getInputWithPrint() {
        consoleOutput.print(INPUT_PREFIX);
        String input = consoleInput.getInput();
        consoleOutput.printEmptyLine();

        return input;
    }

    public String inputStringWithMessage(InputMessage inputMessage) {
        this.printQuestionMessage(inputMessage.getMessage());

        return getInputWithPrint();
    }

    public int inputIntWithMessage(InputMessage inputMessage) {
        this.printQuestionMessage(inputMessage.getMessage());

        return getParseInputWithPrint(Integer::parseInt);
    }

    public long inputLongWithMessage(InputMessage inputMessage) {
        this.printQuestionMessage(inputMessage.getMessage());

        return getParseInputWithPrint(Long::parseLong);
    }

    private <T> T getParseInputWithPrint(Function<String, T> parseFunction) {
        try {
            return parseFunction.apply(getInputWithPrint());
        } catch (NumberFormatException e) {
            throw new InputException(NOT_NUMBER);
        }
    }

    public BookSaveRequest inputBookInfo() {
        String title = inputStringWithMessage(TITLE);
        String author = inputStringWithMessage(AUTHOR);
        int pageCount = inputIntWithMessage(PAGE_COUNT);

        return new BookSaveRequest(title, author, pageCount);
    }


    // Output
    public void printSystemMessage(String message) {
        consoleOutput.println(SYSTEM_MESSAGE_PREFIX + message);
        consoleOutput.printEmptyLine();
    }

    public void printQuestionMessage(String message) {
        consoleOutput.println(QUESTION_MESSAGE_PREFIX + message);
    }

    public <T> void printList(List<T> list) {
        for (T t : list) {
            consoleOutput.println(t.toString());
            consoleOutput.printEmptyLine();
            consoleOutput.println(SEPARATOR);
            consoleOutput.printEmptyLine();
        }
    }

    public void printEnumString(Class<? extends Enum<?>> e) {
        for (Enum<?> enumConstant : e.getEnumConstants()) {
            consoleOutput.println(enumConstant.toString());
        }
        consoleOutput.printEmptyLine();
    }
}
