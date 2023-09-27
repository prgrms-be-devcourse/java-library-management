package com.programmers.library.repository;

import com.programmers.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface Repository {
    public int generateId();

    public void save(Book book);

    public List<Book> findAll();

    public List<Book> findAllByName(String name);

    public Optional<Book> findOneById(int id);

    public void update(int id, Book updatedBook);

    public void delete(int id);
}
