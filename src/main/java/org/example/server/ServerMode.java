package org.example.server;

import org.example.server.repository.BookRepository;
import org.example.server.repository.CommonBookRepository;
import org.example.server.repository.TestBookRepository;

import java.util.function.Supplier;

public enum ServerMode {
    COMMON(CommonBookRepository::new),
    TEST(TestBookRepository::new);

    private final Supplier<BookRepository> bookRepositorySupplier;

    ServerMode(Supplier<BookRepository> bookRepositorySupplier) {
        this.bookRepositorySupplier = bookRepositorySupplier;
    }

    public BookRepository getRepository() {
        return bookRepositorySupplier.get();
    }

}