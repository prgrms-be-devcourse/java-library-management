package com.programmers.librarymanagement.repository;

import com.programmers.librarymanagement.domain.Book;

import java.util.*;

public class NormalBookRepository implements BookRepository{

    private static final Map<Long, Book> libraryStorage = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public void addBook(Book book) {
        libraryStorage.put(book.getId(), book);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(libraryStorage.get(id));
    }

    @Override
    public List<Book> findByTitle(String title) {
        return libraryStorage.values().stream()
                .filter(book -> book.getTitle().contains(title))
                .toList();
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(libraryStorage.values());
    }

    @Override
    public void updateBook(Book book) {
        libraryStorage.put(book.getId(), book);
    }

    @Override
    public void deleteBook(Book book) {
        libraryStorage.remove(book.getId());
    }

    @Override
    public Long createId() {
        return sequence++;
    }
}
