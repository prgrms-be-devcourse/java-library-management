package com.programmers.librarymanagement.repository;

import com.programmers.librarymanagement.domain.Book;
import com.programmers.librarymanagement.exception.BookNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TestBookRepository implements BookRepository {

    private static final List<Book> testStorage = new ArrayList<>();
    private static long sequence = 1L;

    @Override
    public void addBook(Book book) {
        testStorage.add(book);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return testStorage.stream()
                .filter(book -> book.getId().equals(id))
                .findAny();
    }

    @Override
    public List<Book> findByTitle(String title) {
        return testStorage.stream()
                .filter(book -> book.getTitle().contains(title))
                .toList();
    }

    @Override
    public List<Book> findAll() {
        return testStorage;
    }

    @Override
    public void updateBook(Book book) {

        Book originalBook = findById(book.getId()).orElseThrow(BookNotFoundException::new);

        testStorage.remove(originalBook);
        testStorage.add(book);
    }

    @Override
    public void deleteBook(Book book) {
        testStorage.remove(book);
    }

    @Override
    public Long createId() {
        return sequence++;
    }
}
