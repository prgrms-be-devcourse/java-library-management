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
        communicationAgent.print("[System] 시스템을 종료합니다.");
        System.exit(0);
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
            books.forEach(book -> communicationAgent.print(book.toString()));
        } catch (RuntimeException e) {
            communicationAgent.print(e.getMessage());
        }
    }

    @Override
    public void borrowBook() {
        try {
            bookService.borrowBook(communicationAgent.instructBorrow());
            communicationAgent.print("[System] 도서가 대여 처리 되었습니다.");
        } catch (RuntimeException e) {
            communicationAgent.print(e.getMessage());
        }
    }

    @Override
    public void returnBook() {
        try {
            bookService.returnBook(communicationAgent.instructReturn());
            communicationAgent.print("[System] 도서가 반납 처리 되었습니다.");
        } catch (RuntimeException e) {
            communicationAgent.print(e.getMessage());
        }
    }

    @Override
    public void reportLostBook() {
        try {
            bookService.reportLost(communicationAgent.instructReportLost());
            communicationAgent.print("[System] 도서가 분실 처리 되었습니다.");
        } catch (RuntimeException e) {
            communicationAgent.print(e.getMessage());
        }
    }

    @Override
    public void deleteBook() {
        try {
            bookService.deleteBook(communicationAgent.instructDelete());
            communicationAgent.print("[System] 도서 삭제 처리 메뉴로 넘어갑니다.");
        } catch (RuntimeException e) {
            communicationAgent.print(e.getMessage());
        }
    }
}
