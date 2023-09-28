package org.example.type;

import org.example.server.repository.BookRepository;
import org.example.server.repository.CommonBookRepository;
import org.example.server.repository.TestBookRepository;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ModeType {
    COMMON(1, "1. 일반 모드\n", "\n[System] 일반 모드로 애플리케이션을 실행합니다.\n\n", CommonBookRepository::new),
    TEST(2, "2. 테스트 모드\n", "\n[System] 테스트 모드로 애플리케이션을 실행합니다.\n\n", TestBookRepository::new);

    private final int num;
    private final String name;
    private final String alert;
    private final Supplier<BookRepository> bookRepositorySupplier;

    ModeType(int num, String name, String alert, Supplier<BookRepository> bookRepositorySupplier) {
        this.num = num;
        this.name = name;
        this.alert = alert;
        this.bookRepositorySupplier = bookRepositorySupplier;
    }

    private static final Map<Integer, ModeType> BY_NUMBER =
            Stream.of(values()).collect(Collectors.toMap(ModeType::getNum, Function.identity()));

    public static ModeType valueOfNumber(int num) {
        return BY_NUMBER.get(num);
    }

    public static final String MODE_CONSOLE = "\nQ. 모드를 선택해주세요.\n"
            + String.join("", Stream.of(values()).map(type -> type.name).toArray(String[]::new)) + "\n> ";

    public int getNum() {
        return num;
    }

    public String getAlert() {
        return alert;
    }

    public BookRepository getRepository() {
        return bookRepositorySupplier.get();
    }

}