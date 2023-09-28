package org.example.server;

import org.example.server.entity.Book;
import org.example.server.entity.BookState;
import org.example.server.entity.RequestBookDto;
import org.example.server.repository.BookRepository;

public class BookService {
    public static BookRepository repository;


    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public static void register(RequestBookDto requestBookDto) {
        Book book = new Book(requestBookDto);
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
        book.state = BookState.BORROWED;
    }

    public static void restore(int bookId) {
        Book book = repository.getById(bookId);
        book.state = BookState.CAN_BORROW; // 5분 설정 필요
    }

    public static void lost(int bookId) {
        Book book = repository.getById(bookId);
        book.state = BookState.LOST;
    }

    public static void delete(int bookId) {
        repository.delete(bookId);
    }
}
