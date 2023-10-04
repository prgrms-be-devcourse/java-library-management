package org.library;

import org.library.controller.ModeController;
import org.library.service.BookService;
import org.library.controller.Controller;
import org.library.utils.ConsoleManager;
import org.library.utils.ConsoleInputManager;

public class BookApplication {
    private static final ModeController modeController = new ModeController();
    public static void main(String[] args) {
        BookService bookService = new BookService(modeController.selectMode());
        ConsoleManager consoleManager = new ConsoleManager(new ConsoleInputManager());
        Controller controller = new Controller(bookService, consoleManager);
        while(true) controller.run();
    }
}