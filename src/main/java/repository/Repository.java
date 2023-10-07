package repository;

import domain.Book;

import java.util.List;
import java.util.Optional;

public interface Repository {

    void addBook(Book book);

    List<Book> getAll();

    List<Book> searchBook(String name);

    Optional<Book> getBook(Long bookNumber);

    void deleteBook(Book book);

    void rentalBook(Book book);

    void organizeBook(Book book);

    void returnBook(Book book);

    void lostBook(Book book);
}
