package com.programmers.app.book.repository;

import java.util.List;
import java.util.Optional;

import com.programmers.app.book.domain.Book;

public interface BookRepository {

    void add(Book book);

    long getLastBookNumber();

    Optional<List<Book>> findAllBooks();

    Optional<List<Book>> findByTitle(String title);

    Optional<Book> findByBookNumber(long bookNumber);

    void save(Book book);

    void deleteByBookNumber(long bookNumber);

    void saveFile();
}
