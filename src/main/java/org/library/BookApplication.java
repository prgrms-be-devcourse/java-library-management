package org.library;

import org.library.controller.ModeController;
import org.library.service.BookService;
import org.library.utils.Executor;

public class BookApplication {
    private static final ModeController modeController = new ModeController();
    public static void main(String[] args) {
        Executor executor = new Executor(new BookService(modeController.selectMode()));
        while(true) executor.run();
    }
}