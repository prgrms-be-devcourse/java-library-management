package repository;

import model.Book;

import java.util.List;

public interface Repository {

    void saveBook(Book book);
    List<Book> findAllBook();
    List<Book> findBookByTitle(String title);
    void borrowBook(Long bookNo);
    void returnBook(Long bookNo);
}