package com.programmers.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.programmers.adapter.BookControllerAdapter;
import com.programmers.application.BookService;
import com.programmers.config.enums.Mode;
import com.programmers.config.factory.ModeAbstractFactory;
import com.programmers.domain.entity.Book;
import com.programmers.domain.repository.BookRepository;
import com.programmers.domain.repository.FileProvider;
import com.programmers.domain.status.BookStatus;
import com.programmers.exception.AppExceptionHandler;
import com.programmers.exception.GlobalExceptionHandler;
import com.programmers.exception.checked.InvalidModeNumberException;
import com.programmers.infrastructure.BookStatusDeserializer;
import com.programmers.infrastructure.BookStatusSerializer;
import com.programmers.infrastructure.IO.Console;
import com.programmers.infrastructure.IO.ConsoleInteractionAggregator;
import com.programmers.infrastructure.IO.provider.JsonFileProvider;
import com.programmers.infrastructure.IO.requestCommand.DeleteBookRequestGenerator;
import com.programmers.infrastructure.IO.requestCommand.ExitRequestGenerator;
import com.programmers.infrastructure.IO.requestCommand.MenuRequestGenerator;
import com.programmers.infrastructure.IO.requestCommand.RegisterBookRequestGenerator;
import com.programmers.infrastructure.IO.requestCommand.RentBookRequestGenerator;
import com.programmers.infrastructure.IO.requestCommand.ReportLostBookRequestGenerator;
import com.programmers.infrastructure.IO.requestCommand.ReturnBookRequestGenerator;
import com.programmers.infrastructure.IO.requestCommand.SearchBookByTitleRequestGenerator;
import com.programmers.infrastructure.IO.requestCommand.ViewAllBooksRequestGenerator;
import com.programmers.infrastructure.IO.responseCommand.ResponseExitSender;
import com.programmers.infrastructure.IO.responseCommand.ResponseListBodySender;
import com.programmers.infrastructure.IO.responseCommand.ResponseNoBodySender;
import com.programmers.infrastructure.IO.responseCommand.ResponseSender;
import com.programmers.infrastructure.IO.responseCommand.ResponseSingleBodySender;
import com.programmers.infrastructure.IO.validator.InputValidator;
import com.programmers.infrastructure.provider.MenuRequestProvider;
import com.programmers.infrastructure.provider.MenuResponseProvider;
import com.programmers.mediator.ConsoleRequestProcessor;
import com.programmers.mediator.RequestProcessor;
import com.programmers.presentation.BookController;
import com.programmers.util.BookScheduler;
import com.programmers.util.IdGenerator;
import java.util.List;

public class DependencyInjector {

    private static boolean isInitialized = false;
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
    private FileProvider<Book> fileRepository;
    private List<ResponseSender> menuResponseSenders;
    private MenuResponseProvider menuResponseProvider;

    // TODO: 테스트때문에 생성자에서 안하고 init 으로 바꿈
    private DependencyInjector() {
    }

    private static class SingleInstanceHolder {

        private static final DependencyInjector instance = new DependencyInjector();

    }

    // 동시성 문제때문에... -> 싱글톤 패턴 구현 방법.
    // 실제 사용하는 시점에 초기화 가능, 레이지 로딩 (실제로 사용할때 가져온다) 구현 참고. 레이지 이니셜라이징 (싱글톤에서 쓰는거). 레이지 키워드로 검색
    public synchronized void init() {
        if (isInitialized) {
            return;
        }

        initializeValidatorsAndConsoles();
        initializeGeneratorsAndProviders();
        initializeModeDependentServices();
        initializeServicesAndHandlers();

        isInitialized = true;
    }

    public static DependencyInjector getInstance() {
        return SingleInstanceHolder.instance;
    }

    private void initializeValidatorsAndConsoles() {
        this.userInteractionValidator = new InputValidator();
        this.console = new Console(new java.util.Scanner(System.in), userInteractionValidator);
        //TODO: 테스트때문에 추가
        if (consoleInteractionAggregator == null) {
            this.consoleInteractionAggregator = new ConsoleInteractionAggregator(console);
        }
    }

    private void initializeGeneratorsAndProviders() {
        this.objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(BookStatus.class, new BookStatusDeserializer());
        module.addSerializer(BookStatus.class, new BookStatusSerializer());
        objectMapper.registerModule(module);

        this.fileRepository = new JsonFileProvider(objectMapper);
        this.menuRequestGenerators = List.of(new ExitRequestGenerator(consoleInteractionAggregator),
            new DeleteBookRequestGenerator(consoleInteractionAggregator),
            new RegisterBookRequestGenerator(consoleInteractionAggregator),
            new RentBookRequestGenerator(consoleInteractionAggregator),
            new ReportLostBookRequestGenerator(consoleInteractionAggregator),
            new ReturnBookRequestGenerator(consoleInteractionAggregator),
            new SearchBookByTitleRequestGenerator(consoleInteractionAggregator),
            new ViewAllBooksRequestGenerator(consoleInteractionAggregator)
        );
        this.menuResponseSenders = List.of(new ResponseExitSender(consoleInteractionAggregator)
            , new ResponseNoBodySender(consoleInteractionAggregator),
            new ResponseListBodySender(consoleInteractionAggregator),
            new ResponseSingleBodySender(consoleInteractionAggregator));
        this.menuResponseProvider = new MenuResponseProvider(menuResponseSenders);
        this.menuRequestProvider = new MenuRequestProvider(menuRequestGenerators);
    }

    private void initializeModeDependentServices() {
        ModeAbstractFactory modeFactory = getModeFactory(consoleInteractionAggregator);
        this.bookRepository = modeFactory.createBookRepository(fileRepository);
        this.idGenerator = modeFactory.createIdGenerator();
    }

    private void initializeServicesAndHandlers() {
        this.bookService = new BookService(bookRepository, new BookScheduler());
        this.controller = new BookController(bookService);
        this.requestProcessor = new ConsoleRequestProcessor(new BookControllerAdapter(controller),
            consoleInteractionAggregator, menuRequestProvider, menuResponseProvider);
        this.globalExceptionHandler = new GlobalExceptionHandler(requestProcessor);
        this.appExceptionHandler = new AppExceptionHandler(requestProcessor);
    }


    private ModeAbstractFactory getModeFactory(
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

    public RequestProcessor getRequestProcessor() {
        if (!isInitialized) {
            init();
        }
        return requestProcessor;
    }

    public GlobalExceptionHandler getGlobalExceptionHandler() {
        if (!isInitialized) {
            init();
        }
        return globalExceptionHandler;
    }

    public AppExceptionHandler getAppExceptionHandler() {
        if (!isInitialized) {
            init();
        }
        return appExceptionHandler;
    }

    public IdGenerator getIdGenerator() {
        if (!isInitialized) {
            init();
        }
        return idGenerator;
    }
}
