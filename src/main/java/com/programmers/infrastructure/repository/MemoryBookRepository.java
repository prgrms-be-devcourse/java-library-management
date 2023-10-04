package com.programmers.infrastructure.repository;

import com.programmers.domain.entity.Book;
import com.programmers.domain.repository.BookRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryBookRepository implements BookRepository {
    final ConcurrentHashMap<Long, Book> books = new ConcurrentHashMap<>();

    @Override
    public Book save(Book book) {
        books.put(book.getId(), book);
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(books.get(id));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    @Override
    public int deleteById(Long id) {
        return books.remove(id) != null ? 1 : 0;
    }

    @Override
    public int update(Book book) {
        return books.replace(book.getId(), book) != null ? 1 : 0;
    }

    @Override
    public List<Book> findByTitle(String title) {
        return books.values().stream().filter(book -> book.getTitle().contains(title)).toList();
    }

}
