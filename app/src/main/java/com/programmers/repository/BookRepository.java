package com.programmers.repository;

import com.programmers.domain.Book;
import com.programmers.domain.BookState;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    public void addBook(Book Book);

    public List<Book> getAllBooks();

    public Optional<Book> findBookById(int id);

    public Optional<Book> findBookByTitle(String title);

    public void updateBookState(int id, BookState bookState);

    public void deleteBook(int id);

    public int createUniqueId();
}
