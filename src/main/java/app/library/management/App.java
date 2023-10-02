package app.library.management;

import app.library.management.config.Configuration;
import app.library.management.core.controller.BookController;
import app.library.management.infra.console.Console;
import app.library.management.infra.mode.ExecutionMode;

public class App {

    private final Console console;
    private final ExecutionMode executionMode;
    private final Configuration configuration;

    private final BookController bookController;

    public App() {
        this.console = new Console();
        console.selectMode();
        this.executionMode = ExecutionMode.fromNum(console.inputInt());
        this.configuration = new Configuration(executionMode);
        this.bookController = configuration.bookController();
    }

    public void logic () {

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
