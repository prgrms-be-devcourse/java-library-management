package com.libraryManagement.service;

import com.libraryManagement.model.domain.Book;
import com.libraryManagement.repository.Repository;

import java.util.List;

public class BookService {
    private final Repository repository;

    public BookService(Repository repository) {
        this.repository = repository;
    }

    public void insertBook(Book book) {
        repository.bookInsert(book);
    }

    public List<Book> findBooks() {
        return repository.findAll();
    }

    public Book findBook(String str) {
        return repository.findOne(str);
    }

    public Book findBook(long id) {
        return repository.findOne(id);
    }
}
