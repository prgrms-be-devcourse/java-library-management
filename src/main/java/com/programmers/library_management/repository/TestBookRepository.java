package com.programmers.library_management.repository;

import com.programmers.library_management.domain.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TestBookRepository implements BookRepository{

    private final Map<Integer, Book> bookMemory;
    public TestBookRepository() {
        this.bookMemory = new HashMap<>();
    }

    @Override
    public void save(Book book) {
        bookMemory.put(book.getBookNumber(), book);
    }

    @Override
    public Optional<Book> findByBookNumber(int bookNumber) {
        return Optional.ofNullable(bookMemory.get(bookNumber));
    }

    @Override
    public List<Book> findByTitle(String searchText) {
        return bookMemory.values()
                .stream()
                .filter(book -> book.getTitle().contains(searchText))
                .toList();
    }

    @Override
    public List<Book> findAll() {
        return bookMemory.values()
                .stream()
                .toList();
    }

    @Override
    public void delete(Book book) {
        bookMemory.remove(book.getBookNumber());
    }
}
