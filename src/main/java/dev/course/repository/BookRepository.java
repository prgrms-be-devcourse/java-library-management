package dev.course.repository;

import dev.course.domain.Book;
import dev.course.domain.BookState;

import java.util.*;

public interface BookRepository {

    Long createBookId();
    Optional<Book> findById(Long bookId);
    void add(Book obj);
    void delete(Long bookId);
    void update(Book obj);
    List<Book> getAll();
    List<Book> findByTitle(String title);
    int getSize();
}
