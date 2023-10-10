import controller.BookController;
import io.Console;


public class Application {

    public static void main(String[] args) {
        Console console = new Console();
        BookController bookController = new BookController(console, console);
        if (bookController.chooseMode()) {
            bookController.runApplication();
        }
    }
}
