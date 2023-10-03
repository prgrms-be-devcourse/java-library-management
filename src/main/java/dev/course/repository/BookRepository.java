package dev.course.repository;

import dev.course.domain.Book;

import java.util.*;

public interface BookRepository {

    Long createBookId();
    Optional<Book> findById(Long bookId);
    void add(Book obj);
    void delete(Long bookId);
    List<Book> getAll();
    List<Book> findByTitle(String title);
    int getSize();
}
