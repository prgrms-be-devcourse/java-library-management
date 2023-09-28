package com.programmers.app.book;

import java.util.Map;

public interface BookService {

    void register(Book book);

    Map<Integer, Book> findAllBooks();

    Book findByTitle(String title);

    void borrowBook(long bookNumber);

    void returnBook(long bookNumber);

    void deleteBook(long bookNumber);

    void reportLost(long bookNumber);
}
