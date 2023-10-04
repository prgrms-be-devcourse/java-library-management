package org.library.repository;


import java.util.List;
import org.library.entity.Book;

public interface Repository {

    Long generatedId();

    void save(Book book);

    List<Book> findAll();

    List<Book> findByTitle(String title);

    Book findById(Long id);

    void delete(Book book);

    void flush();
}
