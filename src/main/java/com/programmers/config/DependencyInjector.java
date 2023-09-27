package com.programmers.config;

import com.programmers.application.BookService;
import com.programmers.common.Validator;
import com.programmers.config.enums.Mode;
import com.programmers.domain.repository.BookRepository;
import com.programmers.exception.GlobalExceptionHandler;
import com.programmers.infrastructure.IO.Console;
import com.programmers.infrastructure.IO.ConsoleRequestProcessor;
import com.programmers.infrastructure.IO.validator.InputValidator;
import com.programmers.mediator.RequestProcessor;
import com.programmers.presentation.ConsoleController;
import com.programmers.presentation.Controller;
import com.programmers.presentation.UserInteraction;

public class DependencyInjector {
    private static final DependencyInjector instance = new DependencyInjector();

    private final BookRepository bookRepository;
    private final RequestProcessor requestProcessor;
    private final UserInteraction userInteraction;
    private final Controller controller;
    private final BookService bookService;
    private final GlobalExceptionHandler globalExceptionHandler;
    private final Validator userInteractionValidator;
    private DependencyInjector() {
        this.userInteractionValidator = new InputValidator();
        this.userInteraction = new Console(userInteractionValidator);
        this.bookRepository = Mode.getBookRepositoryByMode(userInteraction);
        this.bookService = new BookService(bookRepository);
        this.controller = new ConsoleController(bookService);
        this.requestProcessor = new ConsoleRequestProcessor(controller,userInteraction);
        this.globalExceptionHandler = new GlobalExceptionHandler(userInteraction);
    }

    public static DependencyInjector getInstance() {
        return instance;
    }

    public RequestProcessor getRequestProcessor() {
        return requestProcessor;
    }

    public UserInteraction getUserInteraction() {
        return userInteraction;
    }

    public Controller getController() {
        return controller;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public BookService getBookService() {
        return bookService;
    }

    public GlobalExceptionHandler getGlobalExceptionHandler() {
        return globalExceptionHandler;
    }

    public Validator getUserInteractionValidator() {
        return userInteractionValidator;
    }
}
