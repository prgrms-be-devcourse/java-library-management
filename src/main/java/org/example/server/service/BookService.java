package org.example.server.service;

import org.example.packet.dto.BookDto;
import org.example.server.entity.Book;
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
        LinkedList<BookDto> bookDtos = new LinkedList<>();
        repository.getAll().forEach(book -> {
            bookDtos.add(new BookDto(book));
        });
        return bookDtos;
    }

    public LinkedList<BookDto> searchByName(String name) {
        LinkedList<BookDto> bookDtos = new LinkedList<>();
        repository.getByName(name).forEach(book -> {
            bookDtos.add(new BookDto(book));
        });
        return bookDtos;
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
