package com.programmers.librarymanagement.repository;

import com.programmers.librarymanagement.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    void addBook(Book book);

    Optional<Book> findById(Long id);

    List<Book> findByTitle(String title);

    List<Book> findAll();

    void updateBook(Book book);

    void deleteBook(Book book);

    Long createId();
}
