package org.example.server.service;

import org.example.server.entity.Book;
import org.example.server.entity.BookState;
import org.example.server.repository.Repository;

import java.util.Calendar;
import java.util.Date;

public class BookService implements Service {
    private final Repository repository;

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
        BookState bookState = BookState.valueOf(book.state);
        if (bookState.equals(BookState.BORROWED) || bookState.equals(BookState.LOADING) || bookState.equals(BookState.LOST))
            throw bookState.throwStatusException();
        book.state = BookState.BORROWED.name();
    }

    public void restore(int bookId) {
        Book book = repository.getById(bookId);
        BookState bookState = BookState.valueOf(book.state);
        if (bookState.equals(BookState.CAN_BORROW) || bookState.equals(BookState.LOADING))
            throw bookState.throwStatusException();
        book.state = BookState.LOADING.name();
        Date curr = new Date();
        Book.calendar.setTime(curr);
        Book.calendar.add(Calendar.MINUTE, 5);
        book.endLoadTime = Book.format.format(new Date(Book.calendar.getTimeInMillis()));
    }

    public void lost(int bookId) {
        Book book = repository.getById(bookId);
        BookState bookState = BookState.valueOf(book.state);
        if (bookState.equals(BookState.LOST))
            throw bookState.throwStatusException();
        if (bookState.equals(BookState.LOADING)) {
            book.endLoadTime = "";
        }
        book.state = BookState.LOST.name();
    }

    public void delete(int bookId) {
        repository.getById(bookId);
        repository.delete(bookId);
    }
}
