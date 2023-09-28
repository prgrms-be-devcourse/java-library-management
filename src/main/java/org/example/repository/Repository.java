package org.example.repository;

import org.example.domain.Book;
import org.example.domain.BookStatus;

import java.util.List;
import java.util.Optional;

public interface Repository {
    void saveBook(Book book);

    List<Book> findAllBooks();

    List<Book> findBookByTitle(String title);

    Optional<Book> findBookById(Integer bookId);

    void updateBookStatus(Integer bookId, BookStatus status);

    void deleteBookById(Integer bookId);

    Integer getNextBookId();
}
