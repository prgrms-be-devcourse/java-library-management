package repository;

import domain.Book;

import java.util.List;

public interface Repository {

    void addBook(Book book);

    List<Book> getAll();

    List<Book> searchBook(String name);

    Book getBook(Long bookNumber);

    void deleteBook(Long bookNumber);
}
