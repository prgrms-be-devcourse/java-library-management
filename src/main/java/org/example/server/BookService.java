package org.example.server;

import org.example.server.entity.Book;
import org.example.server.entity.BookState;
import org.example.server.exception.BookBorrowedException;
import org.example.server.exception.BookCanBorrowException;
import org.example.server.exception.BookLoadingException;
import org.example.server.exception.BookLostException;
import org.example.server.repository.BookRepository;

import static org.example.server.entity.BookState.LOADING;

public class BookService {
    private static BookRepository repository;

    public static void setRepository(BookRepository repository) {
        BookService.repository = repository;
    }

    public static void register(String name, String author, int pages) {
        Book book = new Book(name, author, pages);
        repository.create(book);
    }

    public static String readAll() {
        return repository.readAll();
    }

    public static String searchByName(String bookName) {
        return repository.searchByName(bookName);
    }

    public static void borrow(int bookId) {
        Book book = repository.getById(bookId);
        switch (book.state) {
            case BORROWED -> throw new BookBorrowedException();
            case LOADING -> throw new BookLoadingException();
            case LOST -> throw new BookLostException();
        }
        book.state = BookState.BORROWED;
    }

    public static void restore(int bookId) {
        Book book = repository.getById(bookId);
        switch (book.state) {
            case CAN_BORROW -> throw new BookCanBorrowException();
            case LOADING -> throw new BookLoadingException();
        }
        book.state = BookState.CAN_BORROW; // 5분 설정 필요
    }

    public static void lost(int bookId) {
        Book book = repository.getById(bookId);
        switch (book.state) {
            case LOADING -> throw new BookLoadingException();
            case LOST -> throw new BookLostException();
        }
        book.state = BookState.LOST;
    }

    public static void delete(int bookId) {
        Book book = repository.getById(bookId);
        if (book.state == LOADING)
            throw new BookLoadingException();
        repository.delete(bookId);
    }
}
