package com.programmers.library_management.repository;

import com.programmers.library_management.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    void save(Book book);

    Optional<Book> findById(int id);

    List<Book> findByTitle(String searchText);

    List<Book> findAll();

    int generateBookId();

    void delete(Book book);

    void updateAllBookStatus();

    void deleteAll();
}
