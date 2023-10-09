package com.programmers.library.repository;

import com.programmers.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface Repository {
    int generateId();

    void save(Book book);

    List<Book> findAll();

    List<Book> findAllByName(String name);

    Optional<Book> findOneById(int id);

    void update(int id, Book updatedBook);

    void delete(int id);

    void deleteAll();
}
