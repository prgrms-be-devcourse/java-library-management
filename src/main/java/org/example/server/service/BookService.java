package org.example.server.service;

import org.example.server.entity.Book;
import org.example.server.entity.BookState;
import org.example.server.repository.Repository;

import static org.example.server.entity.BookState.*;

public class BookService implements Service {
    private final Repository repository; // 외부에서 repository 의존성 주입

    public BookService(Repository repository) {
        this.repository = repository;
    }

    public void register(String name, String author, int pages) {
        Book book = new Book(name, author, pages);
        repository.create(book);
    }

    public String readAll() {
        return repository.readAll();
    }

    public String searchByName(String bookName) {
        return repository.searchByName(bookName);
    }

    public void borrow(int bookId) {
        Book book = repository.getById(bookId);
        if (book.state.equals(BORROWED) || book.state.equals(LOADING) || book.state.equals(LOST))
            throw book.state.throwStatusException();
        book.state = BookState.BORROWED;
    }

    public void restore(int bookId) {
        Book book = repository.getById(bookId);
        if (book.state.equals(CAN_BORROW) || book.state.equals(LOADING))
            throw book.state.throwStatusException();
        book.state = BookState.CAN_BORROW; // 5분 설정 필요
    }

    public void lost(int bookId) {
        Book book = repository.getById(bookId);
        if (book.state.equals(LOADING) || book.state.equals(LOST))
            throw book.state.throwStatusException();
        book.state = BookState.LOST;
    }

    public void delete(int bookId) {
        Book book = repository.getById(bookId);
        if (book.state.equals(LOADING))
            throw book.state.throwStatusException();
        repository.delete(bookId);
    }
}
