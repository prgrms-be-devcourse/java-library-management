package com.programmers.library_management.repository;

import com.programmers.library_management.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    void save(Book book);
    Optional<Book> findByBookNumber(int bookNumber);
    List<Book> findByTitle(String searchText);
    List<Book> findAll();
    int generateBookNumber();
    void delete(Book book);
}
