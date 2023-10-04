package repository;

import domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    void register(Book book);

    List<Book> getBookList();

    List<Book> findByTitle(String title);

    void borrow(Book book);

    void returnBook(Book book);

    void report(Book book);

    void remove(Book book);

    Optional<Book> findById(Integer id);

    Integer createId();
}
