package library.view;

import library.exception.InputException;
import library.view.console.ConsoleIOHandler;
import library.view.constant.InputMessage;
import library.view.function.BookFunction;
import library.view.function.BookFunctionHandler;

import static library.exception.InputErrorMessage.INVALID_INPUT;

public class ConsoleEngine implements Runnable {

    private final ConsoleIOHandler consoleIOHandler;
    boolean isRunning = true;

    public ConsoleEngine(ConsoleIOHandler consoleIOHandler) {
        this.consoleIOHandler = consoleIOHandler;
    }

    @Override
    public void run() {
        BookFunctionHandler bookFunctionHandler = initializeMode();
        while (isRunning) {
            progress(bookFunctionHandler);
        }
    }

    private BookFunctionHandler initializeMode() {
        Mode mode = selectMode();
        consoleIOHandler.printSystemMessage(mode.getExecutionMessage());
        return new BookFunctionHandler(mode.getBookRepository(), consoleIOHandler);
    }

    private Mode selectMode() {
        try {
            consoleIOHandler.printQuestionMessage(InputMessage.MODE.getMessage());
            consoleIOHandler.printEnumString(Mode.class);

            String input = consoleIOHandler.getInputWithPrint();

            return Mode
                    .findByCode(input)
                    .orElseThrow(() -> new InputException(INVALID_INPUT));
        } catch (RuntimeException e) {
            consoleIOHandler.printSystemMessage(e.getMessage());
            return selectMode();
        }
    }

    private void progress(BookFunctionHandler bookFunctionHandler) {
        try {
            consoleIOHandler.printQuestionMessage(InputMessage.MENU.getMessage());
            consoleIOHandler.printEnumString(BookFunction.class);

            String input = consoleIOHandler.getInputWithPrint();

            BookFunction.findByCode(input)
                    .ifPresentOrElse(
                            bookFunction -> executeBookFunction(bookFunctionHandler, bookFunction),
                            () -> {
                                throw new InputException(INVALID_INPUT);
                            });
        } catch (RuntimeException e) {
            consoleIOHandler.printSystemMessage(e.getMessage());
        } catch (Exception e) {
            isRunning = false;
            consoleIOHandler.printSystemMessage(e.getMessage());
        }
    }

    private void executeBookFunction(BookFunctionHandler bookFunctionHandler, BookFunction bookFunction) {
        consoleIOHandler.printSystemMessage(bookFunction.getMenuEntranceMessage());
        bookFunction.execute(bookFunctionHandler);
        consoleIOHandler.printSystemMessage(bookFunction.getMenuSuccessMessage());
    }
}
