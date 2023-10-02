package devcourse.backend;

import devcourse.backend.business.BookService;
import devcourse.backend.business.ModeType;
import devcourse.backend.view.Console;

public class App {
    public static void main(String[] args) {
        BookService service = new BookService(ModeType.getByNumber(Console.selectMode()));
        Console view = new Console(service);

        view.run();
    }
}