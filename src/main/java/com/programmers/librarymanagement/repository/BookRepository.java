package com.programmers.librarymanagement.repository;

import com.programmers.librarymanagement.domain.Book;
import com.programmers.librarymanagement.domain.Status;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    void addBook(Book book);

    Optional<Book> findById(Long id);

    Optional<Book> findByTitle(String title);

    List<Book> findAll();

    void updateStatus(Status status);

    void deleteBook(Book book);

    Long createId();
}
