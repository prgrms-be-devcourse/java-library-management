package com.programmers.app.book.controller;

import java.util.List;

import com.programmers.app.book.domain.Book;
import com.programmers.app.book.service.BookService;
import com.programmers.app.console.CommunicationAgent;

public class BookControllerImpl implements BookController {
    private final BookService bookService;
    private final CommunicationAgent communicationAgent;

    public BookControllerImpl(BookService bookService, CommunicationAgent communicationAgent) {
        this.bookService = bookService;
        this.communicationAgent = communicationAgent;
    }

    @Override
    public void exit() {
        communicationAgent.printExitMessage();
        System.exit(0);
    }

    @Override
    public void register() {
        bookService.register(communicationAgent.instructRegister());
        communicationAgent.printOperationCompleted("등록");
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
    public void borrowBook() {
        try {
            bookService.borrowBook(communicationAgent.instructBorrow());
            communicationAgent.printOperationCompleted("대여");
        } catch (RuntimeException e) {
            communicationAgent.printError(e);
        }
    }

    @Override
    public void returnBook() {
        try {
            bookService.returnBook(communicationAgent.instructReturn());
            communicationAgent.printOperationCompleted("반납");
        } catch (RuntimeException e) {
            communicationAgent.printError(e);
        }
    }

    @Override
    public void reportLostBook() {
        try {
            bookService.reportLost(communicationAgent.instructReportLost());
            communicationAgent.printOperationCompleted("분실");
        } catch (RuntimeException e) {
            communicationAgent.printError(e);
        }
    }

    @Override
    public void deleteBook() {
        try {
            bookService.deleteBook(communicationAgent.instructDelete());
            communicationAgent.printOperationCompleted("삭제");
        } catch (RuntimeException e) {
            communicationAgent.printError(e);
        }
    }
}
