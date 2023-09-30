package com.programmers.library_management.repository;

import com.programmers.library_management.domain.Book;

import java.util.*;

public class TestBookRepository implements BookRepository {

    private final Map<Integer, Book> bookMemory;

    public TestBookRepository() {
        this.bookMemory = new HashMap<>();
    }

    @Override
    public void save(Book book) {
        bookMemory.put(book.getId(), book);
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
        bookMemory.remove(book.getId());
    }

    @Override
    public void updateAllBookStatus() {
        for (Book book : bookMemory.values()) {
            if (book.isOrganized()) {
                book.available();
            }
        }
    }

    @Override
    public int generateBookNumber() {
        int max = bookMemory.keySet().stream().max(Integer::compareTo).orElse(0);
        for (int i = 1; i <= max; i++) {
            if (!bookMemory.containsKey(i)) {
                return i;
            }
        }
        return max + 1;
    }
}
