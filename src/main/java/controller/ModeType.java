package controller;

import domain.Book;
import lombok.Getter;
import manager.FileManager;
import manager.OutputManager;
import repository.NormalRepository;
import repository.TestRepository;
import service.BookService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum ModeType {
    NORMAL("1", "일반 모드로 애플리케이션을 실행합니다.", loadBooks()),
    TEST("2", "테스트 모드로 애플리케이션을 실행합니다.", new ArrayList<>());

    private final String number;
    private final String message;
    private final List<Book> books;
    private static final String PATH = "/src/main/resources/book_data.csv";

    private static List<Book> loadBooks() {
        FileManager fileManager = new FileManager(PATH);
        return fileManager.loadData();
    }

    private static BookService loadServiceNormal(OutputManager ioManager) {
        return new BookService(new NormalRepository(PATH, NORMAL.getBooks()), ioManager);
    }

    private static BookService loadServiceTest(OutputManager ioManager) {
        return new BookService(new TestRepository(TEST.getBooks()), ioManager);
    }

    ModeType(String number, String message, List<Book> books) {
        this.number = number;
        this.message = message;
        this.books = books;
    }

    public static ModeType findMode(String mode) {
        return Arrays.stream(ModeType.values())
                .filter(modeType -> modeType.getNumber().equals(mode))
                .findFirst()
                .orElse(null);
    }

    public static BookService findService(ModeType modeType, OutputManager ioManager) {
        if (modeType == NORMAL) return loadServiceNormal(ioManager);
        return loadServiceTest(ioManager);
    }


}
