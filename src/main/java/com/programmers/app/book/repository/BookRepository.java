package com.programmers.app.book.repository;

import java.util.List;
import java.util.Optional;

import com.programmers.app.book.domain.Book;
import com.programmers.app.book.domain.BookStatus;

public interface BookRepository {

     void add(Book book);

     int getLastBookNumber();

     Optional<List<Book>> findAllBooks();

    Optional<List<Book>> findByTitle(String title);

    Optional<Book> findByBookNumber(int bookNumber);

    void delete(Book book);

    Book updateBookIfArranged(Book book);

    void updateBookStatus(Book book, BookStatus bookStatus);

    default void save() {}
}
