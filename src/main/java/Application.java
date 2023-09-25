import controller.BookController;
import io.Input;
import io.Output;
import repository.Repository;
import repository.RepositoryInjector;


public class Application {

    public static void main(String[] args) {

        Output.printModeOptions();
        int mode = Input.inputNumber();
        Repository repository = RepositoryInjector.getRepository(mode);

        BookController bookController = new BookController(repository);
        bookController.runApplication();
    }
}
