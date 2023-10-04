package org.library;

import org.library.controller.ModeController;
import org.library.service.BookService;
import org.library.controller.Controller;
import org.library.utils.ConsoleInputManager;
import org.library.utils.InputManager;

public class BookApplication {
    private static final ModeController modeController = new ModeController();
    public static void main(String[] args) {
        BookService bookService = new BookService(modeController.selectMode());
        ConsoleInputManager consoleInputManager = new ConsoleInputManager(new InputManager());
        Controller controller = new Controller(bookService, consoleInputManager);
        while(true) controller.run();
    }
}