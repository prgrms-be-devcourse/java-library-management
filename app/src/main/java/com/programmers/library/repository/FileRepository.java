package com.programmers.library.repository;

import com.programmers.library.domain.Book;

import java.util.List;
import java.util.Optional;

public class FileRepository implements Repository {

    @Override
    public int generateId() {
        return 0;
    }

    @Override
    public void save(Book book) {

    }

    @Override
    public List<Book> findAll() {
        return null;
    }

    @Override
    public List<Book> findAllByName(String name) {
        return null;
    }

    @Override
    public Optional<Book> findOneById(int id) {
        return Optional.empty();
    }

    @Override
    public void update(int id, Book updatedBook) {

    }

    @Override
    public void delete(int id) {

    }
}
