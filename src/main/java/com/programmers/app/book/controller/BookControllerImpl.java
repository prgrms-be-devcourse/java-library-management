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
    public void register() {
        bookService.register(communicationAgent.instructRegister());
        communicationAgent.print("[System] 도서 등록이 완료되었습니다.");
        //todo: when totalPages is not number
    }

    @Override
    public void findAllBooks() {
        try {
            List<Book> books = bookService.findAllBooks();
            //todo: output view
            books.forEach(b -> communicationAgent.print((b.toString())));
        } catch (RuntimeException e) {
            communicationAgent.print(e.getMessage());
        }
    }

    @Override
    public void searchBookByTitle() {
        try {
            List<Book> books = bookService.findByTitle(communicationAgent.instructFindTitle());
            books.forEach(b -> communicationAgent.print(b.toString()));
        } catch (RuntimeException e) {
            communicationAgent.print(e.getMessage());
        }
    }

    @Override
    public void borrowBook() {

    }

    @Override
    public void returnBook() {

    }

    @Override
    public void reportLostBook() {

    }

    @Override
    public void deleteBook() {

    }
}
