package com.programmers.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.adapter.BookControllerAdapter;
import com.programmers.application.BookService;
import com.programmers.config.enums.Mode;
import com.programmers.config.factory.ModeAbstractFactory;
import com.programmers.domain.repository.BookRepository;
import com.programmers.exception.AppExceptionHandler;
import com.programmers.exception.GlobalExceptionHandler;
import com.programmers.exception.checked.InvalidModeNumberException;
import com.programmers.infrastructure.IO.Console;
import com.programmers.infrastructure.IO.ConsoleInteractionAggregator;
import com.programmers.infrastructure.IO.command.DeleteBookRequestGenerator;
import com.programmers.infrastructure.IO.command.ExitRequestGenerator;
import com.programmers.infrastructure.IO.command.MenuRequestGenerator;
import com.programmers.infrastructure.IO.command.RegisterBookRequestGenerator;
import com.programmers.infrastructure.IO.command.RentBookRequestGenerator;
import com.programmers.infrastructure.IO.command.ReportLostBookRequestGenerator;
import com.programmers.infrastructure.IO.command.ReturnBookRequestGenerator;
import com.programmers.infrastructure.IO.command.SearchBookByTitleRequestGenerator;
import com.programmers.infrastructure.IO.command.ViewAllBooksRequestGenerator;
import com.programmers.infrastructure.IO.validator.InputValidator;
import com.programmers.infrastructure.MenuRequestProvider;
import com.programmers.mediator.ConsoleRequestProcessor;
import com.programmers.mediator.RequestProcessor;
import com.programmers.presentation.BookController;
import com.programmers.util.BookScheduler;
import com.programmers.util.IdGenerator;
import java.util.List;

public class DependencyInjector {

    private static final DependencyInjector instance = new DependencyInjector();
    private BookRepository bookRepository;
    private RequestProcessor requestProcessor;
    private Console console;
    private BookController controller;
    private BookService bookService;
    private GlobalExceptionHandler globalExceptionHandler;
    private InputValidator userInteractionValidator;
    private AppExceptionHandler appExceptionHandler;
    private IdGenerator idGenerator;
    private ConsoleInteractionAggregator consoleInteractionAggregator;
    private ObjectMapper objectMapper;
    private MenuRequestProvider menuRequestProvider;
    private List<MenuRequestGenerator> menuRequestGenerators;

    // TODO: 테스트때문에 생성자에서 안하고 init 으로 바꿈
    public DependencyInjector() {}

    public void init() {
        if (isInitialized()) {
            return;
        }

        initializeValidatorsAndConsoles();
        initializeGeneratorsAndProviders();
        initializeServicesAndHandlers();
    }

    private boolean isInitialized() {
        return requestProcessor != null;
    }

    private void initializeValidatorsAndConsoles() {
        this.userInteractionValidator = new InputValidator();
        this.console = new Console(new java.util.Scanner(System.in),userInteractionValidator);
        //TODO: 테스트때문에 추가
        if(consoleInteractionAggregator == null) this.consoleInteractionAggregator = new ConsoleInteractionAggregator(console);
    }

    private void initializeGeneratorsAndProviders() {
        this.objectMapper = new ObjectMapper();
        this.menuRequestGenerators = List.of(new ExitRequestGenerator(consoleInteractionAggregator),
            new DeleteBookRequestGenerator(consoleInteractionAggregator),
            new RegisterBookRequestGenerator(consoleInteractionAggregator),
            new RentBookRequestGenerator(consoleInteractionAggregator),
            new ReportLostBookRequestGenerator(consoleInteractionAggregator),
            new ReturnBookRequestGenerator(consoleInteractionAggregator),
            new SearchBookByTitleRequestGenerator(consoleInteractionAggregator),
            new ViewAllBooksRequestGenerator(consoleInteractionAggregator)
        );
        this.menuRequestProvider = new MenuRequestProvider(menuRequestGenerators);
    }

    }

    private void initializeServicesAndHandlers() {
        this.bookService = new BookService(bookRepository, new BookScheduler());
        this.controller = new BookController(bookService);
        this.requestProcessor = new ConsoleRequestProcessor(new BookControllerAdapter(controller),
            consoleInteractionAggregator, menuRequestProvider);
        this.globalExceptionHandler = new GlobalExceptionHandler(requestProcessor);
        this.appExceptionHandler = new AppExceptionHandler(requestProcessor);
    }


    private static ModeAbstractFactory getModeFactory(
        ConsoleInteractionAggregator consoleInteractionAggregator) {
        try {
            ModeAbstractFactory modeAbstractFactory = Mode.getModeFactory(
                consoleInteractionAggregator.collectModeInput());
            consoleInteractionAggregator.displayMessage(modeAbstractFactory.getModeMessage());
            return modeAbstractFactory;
        } catch (InvalidModeNumberException e) {
            consoleInteractionAggregator.displayMessage(e.getErrorCode().getMessage());
            return getModeFactory(consoleInteractionAggregator);
        }
    }

    public static DependencyInjector getInstance() {
        return instance;
    }

    public RequestProcessor getRequestProcessor() {
        return requestProcessor;
    }

    public GlobalExceptionHandler getGlobalExceptionHandler() {
        return globalExceptionHandler;
    }

    public AppExceptionHandler getAppExceptionHandler() {
        return appExceptionHandler;
    }

    public IdGenerator getIdGenerator() {
        return idGenerator;
    }
}
