package com.programmers.repository;

import com.programmers.domain.Book;
import com.programmers.domain.BookState;

import java.util.List;

public interface BookRepository {

    public void addBook(Book Book);

    public List<Book> getAllBooks();

    public Book findBookById(int id);

    public Book findBookByTitle(String title);

    public void updateBookState(int id, BookState bookState);

    public void deleteBook(int id);
}
