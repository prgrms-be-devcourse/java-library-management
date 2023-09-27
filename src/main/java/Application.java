import constant.Guide;
import controller.BookController;
import io.Console;


public class Application {

    public static void main(String[] args) throws Exception {

        Console console = new Console();
        console.printModeOptions();
        int mode = console.inputNumber();

        if (mode == 1) {
            console.printGuide(Guide.START_NORMAL_MODE);
        } else if (mode == 2) {
            console.printGuide(Guide.START_TEST_MODE);
        }
        new BookController(mode, console, console).runApplication();
    }
}
