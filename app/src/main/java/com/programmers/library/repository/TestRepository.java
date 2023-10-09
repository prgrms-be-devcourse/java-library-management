package com.programmers.library.repository;

import com.programmers.library.domain.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TestRepository implements Repository {
    private static int sequance = 0;
    private List<Book> storage = new ArrayList<>();

    @Override
    public int generateId() {
        return ++sequance;
    }

    @Override
    public void save(Book book) {
        storage.add(book);
    }

    @Override
    public List<Book> findAll() {
        return storage.stream()
                .toList();
    }

    @Override
    public List<Book> findAllByName(String name) {
        return storage.stream()
                .filter(book -> book.isNameContains(name))
                .toList();
    }

    @Override
    public Optional<Book> findOneById(int id) {
        return storage.stream()
                .filter(book -> book.isIdEqualTo(id))
                .findFirst();
    }

    @Override
    public void update(int id, Book updatedBook) {
        storage.stream()
                .filter(book -> book.isIdEqualTo(id))
                .findFirst()
                .ifPresent(book -> book = updatedBook);
    }

    @Override
    public void delete(int id) {
        storage = storage.stream()
                .filter(book -> !book.isIdEqualTo(id))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }
}
