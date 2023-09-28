package com.programmers.library.repository;

import com.programmers.library.domain.Book;
import com.programmers.library.domain.BookStatus;

import java.util.List;
import java.util.Optional;

public interface Repository {

    public void register(Book book);
    public List<Book> findAllBooks();
    public void deleteBook(Long id);
    public List<Book> findBooksByTitle(String title);
    public Optional<Book> findBookById(Long id);
    public void updateStatus(Book book, BookStatus bookStatus);
    public Long findLastId();

}
