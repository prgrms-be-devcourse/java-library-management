package org.example.server.service;

import org.example.server.entity.Book;
import org.example.server.entity.BookState;
import org.example.server.entity.RequestBookDto;
import org.example.server.repository.BookRepository;

public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public void register(RequestBookDto requestBookDto) {
        Book book = new Book(requestBookDto);
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
        book.state = BookState.BORROWED;
    }

    public void restore(int bookId) {
        Book book = repository.getById(bookId);
        book.state = BookState.CAN_BORROW; // 5분 설정 필요
    }

    public void lost(int bookId) {
        Book book = repository.getById(bookId);
        book.state = BookState.LOST;
    }

    public void delete(int bookId) {
        repository.delete(bookId);
    }
}
