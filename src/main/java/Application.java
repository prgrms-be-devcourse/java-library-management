import controller.BookController;
import io.Console;


public class Application {

    public static void main(String[] args) throws Exception {

        Console console = new Console();
        console.printModeOptions();
        int mode = console.inputNumber();

        new BookController(mode, console, console).runApplication();
    }
}
