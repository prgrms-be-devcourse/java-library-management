package controller.mode;

import lombok.Getter;
import manager.console.OutputManager;
import repository.NormalRepository;
import repository.TestRepository;
import service.BookService;

import java.util.Arrays;

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
        return new BookService(new NormalRepository(PATH));
    }

    private static BookService loadServiceTest() {
        return new BookService(new TestRepository());
    }

    public void printModeExecution() {
        outputManager.printSystem(getMessage());
    }
}
