package com.programmers.infrastructure.repository;

import com.programmers.domain.entity.Book;
import com.programmers.domain.repository.BookRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryBookRepository implements BookRepository {
    final ConcurrentHashMap<String, Book> books = new ConcurrentHashMap<>();

    @Override
    public Book save(Book book) {
        books.put(book.getId(), book);
        return book;
    }

    @Override
    public Optional<Book> findById(String id) {
        return Optional.ofNullable(books.get(id));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    @Override
    public void deleteById(String id) {
        books.remove(id);
    }

    @Override
    public void update(Book book) {

    }

    @Override
    public List<Book> findByTitle(String title) {
        return null;
    }
}
