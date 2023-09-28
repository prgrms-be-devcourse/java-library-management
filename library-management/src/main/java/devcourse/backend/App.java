package devcourse.backend;

import devcourse.backend.business.BookService;
import devcourse.backend.repository.FileRepository;
import devcourse.backend.repository.Repository;
import devcourse.backend.view.Console;

import java.util.NoSuchElementException;

public class App {
    public static void main(String[] args) {
        int mode = Console.selectMode();

        Repository repository = Mode.getRepository(mode);
        BookService service = new BookService(repository);
        Console view = new Console(service);

        view.run();
    }
}