package org.example.server.service;

import org.example.packet.BookDto;
import org.example.server.entity.Book;
import org.example.server.repository.Repository;

import java.util.LinkedList;

public class BookService implements Service {
    private final Repository REPOSITORY;

    public BookService(Repository repository) {
        this.REPOSITORY = repository;
    }

    public void register(BookDto bookDto) {
        REPOSITORY.save(new Book(bookDto));
    }

    public LinkedList<BookDto> readAll() {
        LinkedList<BookDto> bookDtos = new LinkedList<>();
        REPOSITORY.getAll().forEach(book -> {
            bookDtos.add(new BookDto(book));
        });
        return bookDtos;
    }

    public LinkedList<BookDto> searchByName(String name) {
        LinkedList<BookDto> bookDtos = new LinkedList<>();
        REPOSITORY.getByName(name).forEach(book -> {
            bookDtos.add(new BookDto(book));
        });
        return bookDtos;
    }

    public void borrow(int bookId) {
        Book book = REPOSITORY.findById(bookId);
        book.borrow();
    }

    public void restore(int bookId) {
        Book book = REPOSITORY.findById(bookId);
        book.restore();
    }

    public void lost(int bookId) {
        Book book = REPOSITORY.findById(bookId);
        book.lost();
    }

    public void delete(int bookId) {
        REPOSITORY.findById(bookId);
        REPOSITORY.delete(bookId);
    }
}
