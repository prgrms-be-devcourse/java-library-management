package com.programmers.app.book.controller;

import java.util.List;

import com.programmers.app.book.domain.Book;
import com.programmers.app.book.service.BookService;
import com.programmers.app.console.CommunicationAgent;
import com.programmers.app.menu.Menu;

public class BookControllerImpl implements BookController {
    private final BookService bookService;
    private final CommunicationAgent communicationAgent;

    public BookControllerImpl(BookService bookService, CommunicationAgent communicationAgent) {
        this.bookService = bookService;
        this.communicationAgent = communicationAgent;
    }

    @Override
    public void exit() {
        bookService.exit();
        communicationAgent.printExitMessage();
        System.exit(0);
    }

    @Override
    public void register(Menu menu) {
        bookService.register(communicationAgent.instructRegister());
        communicationAgent.printOperationCompleted(menu);
    }

    @Override
    public void findAllBooks() {
        printCatchException(() -> communicationAgent.printFindResult(bookService.findAllBooks()));
    }

    @Override
    public void searchBookByTitle() {
        printCatchException(() -> {
            List<Book> books = bookService.findByTitle(communicationAgent.instructFindTitle());
            communicationAgent.printSearchResult(books);
        });
    }

    @Override
    public void borrowBook(Menu menu) {
        printCatchException(() -> {
            bookService.borrowBook(communicationAgent.instructBorrow());
            communicationAgent.printOperationCompleted(menu);
        });
    }

    @Override
    public void returnBook(Menu menu) {
        printCatchException(() -> {
            bookService.returnBook(communicationAgent.instructReturn());
            communicationAgent.printOperationCompleted(menu);
        });
    }

    @Override
    public void reportLostBook(Menu menu) {
        printCatchException(() -> {
            bookService.reportLost(communicationAgent.instructReportLost());
            communicationAgent.printOperationCompleted(menu);
        });
    }

    @Override
    public void deleteBook(Menu menu) {
        printCatchException(() -> {
            bookService.deleteBook(communicationAgent.instructDelete());
            communicationAgent.printOperationCompleted(menu);
        });
    }

    private void printCatchException(Runnable runnable) {
        try {
            runnable.run();
        } catch (RuntimeException e) {
            communicationAgent.printError(e);
        }
    }
}
