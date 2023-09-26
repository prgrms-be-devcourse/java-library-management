package org.library.repository;


import org.library.entity.Book;

import java.util.List;

public interface Repository {
    void save(Book book);
    List<Book> findAll();
    Book findByTitle(String title);
    Book findById(Long id);
    void delete(Book book);
}
