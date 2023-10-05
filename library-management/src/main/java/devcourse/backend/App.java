package devcourse.backend;

import devcourse.backend.business.BookService;
import devcourse.backend.business.ModeType;
import devcourse.backend.view.Console;

import java.time.Clock;
import java.time.ZoneId;

public class App {
    public static void main(String[] args) {
        BookService service = new BookService(ModeType.getByNumber(Console.selectMode()),
                Clock.system(ZoneId.of("Asia/Seoul")));
        Console view = new Console(service);

        view.run();
    }
}