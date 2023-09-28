package com.programmers.app.book;

import java.util.Map;

public interface BookRepository {

    void loadBooksFromFile();

    void add(Book book);

    Map<Integer, Book> findAllBooks();

    Book findByTitle(String title);

    Book findByBookNumber(long bookNumber);

    void save(Book book);

    void deleteByBookNumber(long bookNumber);

    void saveFile();
}
