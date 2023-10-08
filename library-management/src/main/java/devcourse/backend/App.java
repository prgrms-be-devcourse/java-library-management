package devcourse.backend;

import devcourse.backend.business.BookService;
import devcourse.backend.business.ModeType;
import devcourse.backend.repository.FileRepository;
import devcourse.backend.repository.MemoryRepository;
import devcourse.backend.repository.Repository;
import devcourse.backend.view.Console;

import java.time.Clock;
import java.time.ZoneId;

import static devcourse.backend.FileSetting.FILE_NAME;
import static devcourse.backend.FileSetting.FILE_PATH;
import static devcourse.backend.business.ModeType.*;

public class App {
    public static void main(String[] args) {
        ModeType mode = getByNumber(Console.selectMode());
        if(mode == TEST_MODE) {
            Repository repository = new MemoryRepository();
            BookService service = new BookService(repository);
            Console view = new Console(service);
            view.run();
        }

        if(mode == NORMAL_MODE) {
            Repository repository = new FileRepository(FILE_PATH.getValue(), FILE_NAME.getValue());
            BookService service = new BookService(repository);
            Console view = new Console(service);
            view.run();
        }
    }
}