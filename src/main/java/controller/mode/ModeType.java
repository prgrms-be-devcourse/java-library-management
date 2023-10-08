package controller.mode;

import domain.Book;
import lombok.Getter;
import manager.FileManager;
import manager.console.OutputManager;
import repository.NormalRepository;
import repository.TestRepository;
import service.BookService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum ModeType {
    NORMAL("1", "일반 모드로 애플리케이션을 실행합니다.", loadServiceNormal()),
    TEST("2", "테스트 모드로 애플리케이션을 실행합니다.", loadServiceTest());

    private final String number;
    private final String message;
    private final BookService bookService;
    private static final String PATH = "/src/main/resources/book_data.csv";
    private static final OutputManager outputManager = new OutputManager();

    ModeType(String number, String message, BookService bookService) {
        this.number = number;
        this.message = message;
        this.bookService = bookService;
    }

    public static ModeType findModeTypeByMode(String mode) {
        return Arrays.stream(ModeType.values())
                .filter(modeType -> modeType.getNumber().equals(mode))
                .findFirst()
                .orElse(null);
    }

    private static BookService loadServiceNormal() {
        List<Book> books = loadBooks();
        return new BookService(new NormalRepository(PATH, books), outputManager);
    }

    private static BookService loadServiceTest() {
        List<Book> books = new ArrayList<>();
        return new BookService(new TestRepository(books), outputManager);
    }

    private static List<Book> loadBooks() {
        FileManager fileManager = new FileManager(PATH);
        return fileManager.loadData();
    }

    public void printModeExecution() {
        outputManager.printSystem(getMessage());
    }
}
