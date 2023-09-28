package library.view;

import library.repository.BookRepository;
import library.repository.InMemoryBookRepository;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;

public enum Mode {
    NORMAL("1", "일반 모드", InMemoryBookRepository::new), // TODO: FileBookRepository::new
    TEST("2", "테스트 모드", InMemoryBookRepository::new);

    private final String code;
    private final String description;
    private final Supplier<BookRepository> bookRepositorySupplier;

    Mode(String code, String description, Supplier<BookRepository> bookRepositorySupplier) {
        this.code = code;
        this.description = description;
        this.bookRepositorySupplier = bookRepositorySupplier;
    }

    public static Optional<Mode> findByCode(String code) {
        return Arrays.stream(values())
                .filter(option -> option.code.equals(code))
                .findFirst();
    }

    public String getExecutionMessage() {
        return this.description + "로 애플리케이션을 실행합니다.";
    }

    public BookRepository getBookRepository() {
        return bookRepositorySupplier.get();
    }
}
