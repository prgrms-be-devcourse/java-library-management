package com.programmers.app.book.service;

import java.util.List;

import com.programmers.app.book.domain.Book;
import com.programmers.app.book.dto.BookRequest;

public interface BookService {

    void register(BookRequest bookRequest);

    List<Book> findAllBooks();

    List<Book> findByTitle(String title);

    void borrowBook(int bookNumber);

    void returnBook(int bookNumber);

    void deleteBook(int bookNumber);

    void reportLost(int bookNumber);

    boolean updateArrangementCompleted();
}
