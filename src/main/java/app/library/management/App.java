package app.library.management;

import app.library.management.config.Configuration;
import app.library.management.core.controller.BookController;
import app.library.management.infra.console.Console;
import app.library.management.infra.mode.ExecutionMode;

public class App {

    private final ExecutionMode executionMode;

    public App(ExecutionMode executionMode) {
        this.executionMode = executionMode;
    }

    public void logic () {

        Console console = new Console();
        Configuration configuration = new Configuration(executionMode);
        BookController bookController = configuration.bookController();

        while(true) {
            console.selectMenu();
            int num = console.inputInt();

            switch(num) {
                case 1 : bookController.saveBook(); break;
                case 2 : bookController.findBooks(); break;
                case 3 : bookController.findBooksByTitle(); break;
                case 4 : bookController.rentBook(); break;
                case 5 : bookController.returnBook(); break;
                case 6 : bookController.reportLostBook(); break;
                case 7 : bookController.deleteBook(); break;
                default :
                    break;
            }

            if (!(num >= 1 && num <= 7)) break;
        }

    }
}
