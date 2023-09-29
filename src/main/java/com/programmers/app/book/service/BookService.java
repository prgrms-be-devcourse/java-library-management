package com.programmers.app.book.service;

import java.util.List;

import com.programmers.app.book.domain.Book;
import com.programmers.app.book.request.RequestBook;

public interface BookService {

    void register(RequestBook requestBook);

    List<Book> findAllBooks();

    List<Book> findByTitle(String title);

    void borrowBook(long bookNumber);

    void returnBook(long bookNumber);

    void deleteBook(long bookNumber);

    void reportLost(long bookNumber);
}
