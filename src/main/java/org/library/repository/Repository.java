package org.library.repository;


import org.library.entity.Book;

import java.util.List;

public interface Repository {
    Long generatedId();
    void save(Book book);
    List<Book> findAll();
    List<Book> findByTitle(String title);
    Book findById(Long id);
    void delete(Book book);
    void processAvailable();

    void flush();
}
