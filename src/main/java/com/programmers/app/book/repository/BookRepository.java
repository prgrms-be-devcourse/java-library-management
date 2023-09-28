package com.programmers.app.book.repository;

import java.util.Map;

import com.programmers.app.book.domain.Book;

public interface BookRepository {

    void add(Book book);

    long getLastBookNumber();

    Map<Integer, Book> findAllBooks();

    Book findByTitle(String title);

    Book findByBookNumber(long bookNumber);

    void save(Book book);

    void deleteByBookNumber(long bookNumber);

    void saveFile();
}
