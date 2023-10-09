package com.programmers.app.book.controller;

import java.util.List;

import com.programmers.app.book.domain.Book;
import com.programmers.app.book.dto.BookRequest;
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
    }

    @Override
    public void register(Menu menu) {
        BookRequest newBook = communicationAgent.instructRegister();
        bookService.register(newBook);
        communicationAgent.printOperationCompleted(menu);
    }

    @Override
    public void findAllBooks() {
        printCatchException(() -> {
            List<Book> books = bookService.findAllBooks();
            communicationAgent.printFindResult(books);
        });
    }

    @Override
    public void searchBookByTitle() {
        printCatchException(() -> {
            String partialTitle = communicationAgent.instructFindTitle();
            List<Book> books = bookService.findByTitle(partialTitle);
            communicationAgent.printSearchResult(books);
        });
    }

    @Override
    public void borrowBook(Menu menu) {
        printCatchException(() -> {
            int bookNumber = communicationAgent.instructMenuAction(menu);
            bookService.borrowBook(bookNumber);
            communicationAgent.printOperationCompleted(menu);
        });
    }

    @Override
    public void returnBook(Menu menu) {
        printCatchException(() -> {
            int bookNumber = communicationAgent.instructMenuAction(menu);
            bookService.returnBook(bookNumber);
            communicationAgent.printOperationCompleted(menu);
        });
    }

    @Override
    public void reportLostBook(Menu menu) {
        printCatchException(() -> {
            int bookNumber = communicationAgent.instructMenuAction(menu);
            bookService.reportLost(bookNumber);
            communicationAgent.printOperationCompleted(menu);
        });
    }

    @Override
    public void deleteBook(Menu menu) {
        printCatchException(() -> {
            int bookNumber = communicationAgent.instructMenuAction(menu);
            bookService.deleteBook(bookNumber);
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
