package org.example.server;

import org.example.server.entity.Book;
import org.example.server.entity.BookState;
import org.example.server.repository.BookRepository;

import static org.example.server.entity.BookState.*;

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
        if (book.state.equals(BORROWED) || book.state.equals(LOADING) || book.state.equals(LOST))
            throw book.state.throwStatusException();
        book.state = BookState.BORROWED;
    }

    public static void restore(int bookId) {
        Book book = repository.getById(bookId);
        if (book.state.equals(CAN_BORROW) || book.state.equals(LOADING))
            throw book.state.throwStatusException();
        book.state = BookState.CAN_BORROW; // 5분 설정 필요
    }

    public static void lost(int bookId) {
        Book book = repository.getById(bookId);
        if (book.state.equals(LOADING) || book.state.equals(LOST))
            throw book.state.throwStatusException();
        book.state = BookState.LOST;
    }

    public static void delete(int bookId) {
        Book book = repository.getById(bookId);
        if (book.state.equals(LOADING))
            throw book.state.throwStatusException();
        repository.delete(bookId);
    }
}
