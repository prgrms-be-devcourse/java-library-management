package org.example.server.service;

import org.example.server.entity.Book;
import org.example.packet.BookDto;
import org.example.server.repository.Repository;

import java.util.LinkedList;

public class BookService implements Service {
    private final Repository repository;

    public BookService(Repository repository) {
        this.repository = repository;
    }

    public void register(BookDto bookDto) {
        repository.save(new Book(bookDto));
    }

    public LinkedList<BookDto> readAll() {
        return (LinkedList<BookDto>) repository.getAll().stream().map(BookDto::new).toList();
    }

    public LinkedList<BookDto> searchByName(String name) {
        return (LinkedList<BookDto>) repository.getByName(name).stream().map(BookDto::new).toList();
    }

    public void borrow(int bookId) {
        Book book = repository.findById(bookId);
        book.borrow();
    }

    public void restore(int bookId) {
        Book book = repository.findById(bookId);
        book.restore();
    }

    public void lost(int bookId) {
        Book book = repository.findById(bookId);
        book.lost();
    }

    public void delete(int bookId) {
        repository.findById(bookId);
        repository.delete(bookId);
    }
}
