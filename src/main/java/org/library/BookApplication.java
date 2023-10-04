package org.library;

import org.library.controller.ModeController;
import org.library.service.BookService;
import org.library.controller.Controller;

public class BookApplication {
    private static final ModeController modeController = new ModeController();
    public static void main(String[] args) {
        Controller controller = new Controller(new BookService(modeController.selectMode()));
        while(true) controller.run();
    }
}