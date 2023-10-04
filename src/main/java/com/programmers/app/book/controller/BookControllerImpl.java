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
        try {
            List<Book> books = bookService.findAllBooks();
            communicationAgent.printFindResult(books);
        } catch (RuntimeException e) {
            communicationAgent.printError(e);
        }
    }

    @Override
    public void searchBookByTitle() {
        try {
            List<Book> books = bookService.findByTitle(communicationAgent.instructFindTitle());
            communicationAgent.printSearchResult(books);
        } catch (RuntimeException e) {
            communicationAgent.printError(e);
        }
    }

    @Override
    public void borrowBook(Menu menu) {
        try {
            bookService.borrowBook(communicationAgent.instructBorrow());
            communicationAgent.printOperationCompleted(menu);
        } catch (RuntimeException e) {
            communicationAgent.printError(e);
        }
    }

    @Override
    public void returnBook(Menu menu) {
        try {
            bookService.returnBook(communicationAgent.instructReturn());
            communicationAgent.printOperationCompleted(menu);
        } catch (RuntimeException e) {
            communicationAgent.printError(e);
        }
    }

    @Override
    public void reportLostBook(Menu menu) {
        try {
            bookService.reportLost(communicationAgent.instructReportLost());
            communicationAgent.printOperationCompleted(menu);
        } catch (RuntimeException e) {
            communicationAgent.printError(e);
        }
    }

    @Override
    public void deleteBook(Menu menu) {
        try {
            bookService.deleteBook(communicationAgent.instructDelete());
            communicationAgent.printOperationCompleted(menu);
        } catch (RuntimeException e) {
            communicationAgent.printError(e);
        }
    }
}
