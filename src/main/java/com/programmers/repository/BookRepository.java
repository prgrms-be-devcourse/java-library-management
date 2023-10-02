package com.programmers.repository;

import com.programmers.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> findByBookTitle(String title);
    Optional<Book> findByBookId(Long bookId);
    List<Book> findAll();
    void saveBook(Book book);
    void clear();

}
