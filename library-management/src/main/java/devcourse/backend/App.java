package devcourse.backend;

import devcourse.backend.business.BookService;
import devcourse.backend.medel.FileRepository;
import devcourse.backend.view.Console;

public class App {
    public static void main(String[] args) {
        FileRepository fileRepository = new FileRepository("src/main/resources/도서 목록.csv");
        BookService service = new BookService(fileRepository);
        Console view = new Console(service);

        view.run();
    }
}