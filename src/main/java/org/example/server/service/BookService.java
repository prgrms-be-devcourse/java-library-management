package org.example.server.service;

import org.example.server.entity.Book;
import org.example.server.repository.Repository;

public class BookService implements Service {
    private final Repository repository;

    public BookService(Repository repository) {
        this.repository = repository;
    }

    public void register(String name, String author, int pages) {
        Book book = new Book(name, author, pages);
        repository.create(book);
        repository.save();
    }

    public String readAll() {
        return repository.readAll();
    }

    public String searchByName(String bookName) {
        return repository.searchByName(bookName);
    }

    public void borrow(int bookId) {
        Book book = repository.getById(bookId);
        book.borrow();
        repository.save();
    }

    public void restore(int bookId) {
        Book book = repository.getById(bookId);
        book.restore();
        repository.save();
    }

    public void lost(int bookId) {
        Book book = repository.getById(bookId);
        book.lost();
        repository.save();
    }

    public void delete(int bookId) {
        repository.getById(bookId);
        repository.delete(bookId);
        repository.save();
    }
}
