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

    private final int modeNum;
    private final String modeName;
    private final String modeStartMent;

    private final Supplier<BookRepository> bookRepositorySupplier;

    ModeType(int modeNum, String modeName, String modeStartMent, Supplier<BookRepository> bookRepositorySupplier) {
        this.modeNum = modeNum;
        this.modeName = modeName;
        this.bookRepositorySupplier = bookRepositorySupplier;
        this.modeStartMent = modeStartMent;
    }

    public static final String MODE_CONSOLE = "\nQ. 모드를 선택해주세요.\n" + String.join("", Stream.of(values()).map(type -> type.modeName).toArray(String[]::new)) + "\n> ";

    private static final Map<Integer, ModeType> BY_NUMBER =
            Stream.of(values()).collect(Collectors.toMap(ModeType::getModeNum, Function.identity()));

    public static ModeType valueOfNumber(int number) {
        return BY_NUMBER.get(number);
    }

    public int getModeNum() {
        return modeNum;
    }

    public String getModeName() {
        return modeName;
    }

    public String getModeStartMent() {
        return modeStartMent;
    }

    public BookRepository getModeRepository() {
        return bookRepositorySupplier.get();
    }

}