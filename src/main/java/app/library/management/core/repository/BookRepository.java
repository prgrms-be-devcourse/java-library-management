package app.library.management.core.repository;

import app.library.management.core.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Book save(Book book);
    List<Book> findAll();
    List<Book> findByTitle(String title);
    Optional<Book> findById(long id);
    void delete(Book book);
    void update(Book book);

}
