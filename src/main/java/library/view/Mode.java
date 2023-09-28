package library.view;

import library.repository.BookRepository;
import library.repository.InMemoryBookRepository;

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
}
