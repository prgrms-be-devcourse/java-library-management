package org.library;

import org.library.controller.ModeController;
import org.library.service.BookService;
import org.library.controller.Controller;
import org.library.utils.ConsoleManager;
import org.library.utils.ConsoleInputManager;

public class BookApplication {
    private static final String path = "/Users/soseungsoo/Desktop/java-library/src/main/resources/json/Book.json";
    public static void main(String[] args) {
        ConsoleInputManager consoleInputManager = new ConsoleInputManager();
        ConsoleManager consoleManager = new ConsoleManager(consoleInputManager);
        ModeController modeController = new ModeController(consoleInputManager, path);
        BookService bookService = new BookService(modeController.selectMode());

        Controller controller = new Controller(bookService, consoleManager);
        while(true) controller.run();
    }
}