package controller;

import domain.Book;
import manager.FileManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public enum ModeType {
    NORMAL("1", "일반 모드로 애플리케이션을 실행합니다.",loadBooks()),
    TEST("2", "테스트 모드로 애플리케이션을 실행합니다.",new ArrayList<>());

    private final String number;
    private final String message;
    private final List<Book> books;
    private static final String PATH = "/src/main/resources/book_data.csv";
    private static final FileManager fileManager = new FileManager(PATH);

    private static  List<Book> loadBooks(){
        return fileManager.loadData();
    }

    public String getNumber() {
        return number;
    }

    public String getMessage() {
        return message;
    }

    public List<Book> getBooks() {
        return books;
    }

    ModeType(String number, String message, List<Book> books) {
        this.number = number;
        this.message = message;
        this.books = books;
    }

    public static ModeType findByMode(String mode) {
        return Arrays.stream(ModeType.values())
                .filter(modeType -> modeType.getNumber().equals(mode))
                .findFirst()
                .orElse(null);
    }


}
