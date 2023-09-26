import controller.BookController;
import io.Console;
import repository.Repository;
import repository.RepositoryInjector;


public class Application {

    public static void main(String[] args) {
        Console console = new Console();

        console.printModeOptions();
        int mode = console.inputNumber();

        Repository repository = RepositoryInjector.getRepository(mode);
        new BookController(repository, console, console).runApplication();
    }
}
