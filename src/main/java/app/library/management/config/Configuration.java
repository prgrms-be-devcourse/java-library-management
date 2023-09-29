package app.library.management.config;

import app.library.management.infra.port.Request;
import app.library.management.infra.console.Input;
import app.library.management.infra.console.Output;
import app.library.management.core.controller.BookController;
import app.library.management.core.domain.util.FileStatusManager;
import app.library.management.infra.port.Response;
import app.library.management.core.repository.file.FileStorage;
import app.library.management.core.repository.BookRepository;
import app.library.management.core.repository.file.FileStorageAdaptor;
import app.library.management.core.repository.memory.BookMemoryRepository;
import app.library.management.core.service.BookService;
import app.library.management.infra.mode.ExecutionMode;

public class Configuration {

    private final ExecutionMode executionMode;

    public Configuration(ExecutionMode executionMode) {
        this.executionMode = executionMode;
    }

    private BookRepository bookRepository() {
        BookRepository bookRepository = null;
        switch (executionMode) {
            case GENERAL:
                bookRepository = new FileStorageAdaptor(fileStorage());
                break;
            case TEST:
                bookRepository = new BookMemoryRepository();
                break;
        }
        return bookRepository;
    }

    private BookService bookService() {
        return new BookService(bookRepository(), statusManager());
    }

    public BookController bookController() {
        return new BookController(bookService(), request(), response());
    }

    private FileStatusManager statusManager() {
        return new FileStatusManager(bookRepository());
    }

    private FileStorage fileStorage() {
        return new FileStorage();
    }
    private Request request() {
        return new Input();
    }

    private Response response() {
        return new Output();
    }
}
