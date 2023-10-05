package app.library.management.config;

import app.library.management.config.factory.BookRepositoryFactory;
import app.library.management.config.util.PropertiesUtil;
import app.library.management.core.domain.util.BookStatusManager;
import app.library.management.core.domain.util.SchedulerManager;
import app.library.management.infra.port.Request;
import app.library.management.infra.console.Input;
import app.library.management.infra.console.Output;
import app.library.management.core.controller.BookController;
import app.library.management.infra.port.Response;
import app.library.management.core.repository.BookRepository;
import app.library.management.core.service.BookService;
import app.library.management.infra.mode.ExecutionMode;

public class Configuration {

    private final ExecutionMode executionMode;
    private final BookRepositoryFactory bookRepositoryFactory;
    private final SchedulerManager schedulerManager;

    public Configuration(ExecutionMode executionMode) {
        this.executionMode = executionMode;
        this.bookRepositoryFactory = new BookRepositoryFactory();
        this.schedulerManager = new SchedulerManager();
    }

    private BookRepository bookRepository() {
        return bookRepositoryFactory.getInstance(executionMode);
    }

    private BookStatusManager statusManager() {
        return new BookStatusManager(bookRepository(), schedulerManager.getScheduler(),
                Integer.parseInt(PropertiesUtil.getProperty("delayMillisecond")));
    }

    private BookService bookService() {
        return new BookService(bookRepository(), statusManager());
    }

    public BookController bookController() {
        return new BookController(bookService(), request(), response());
    }
    private Request request() {
        return new Input();
    }

    private Response response() {
        return new Output();
    }
}
