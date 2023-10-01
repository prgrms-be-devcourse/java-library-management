package dev.course.repository;

import dev.course.domain.Book;
import dev.course.domain.BookState;

import java.util.*;
import java.util.stream.Collectors;

public class TestBookRepository implements BookRepository {

    private final Map<Long, Book> storage;

    public TestBookRepository() {
        this.storage = new TreeMap<>();
    }

    @Override
    public Long createBookId() {
        
        Long bookId = 1L;

        List<Book> bookList = getAll();
        for (Book book : bookList) {
            if (book.getBookId().equals(bookId)) bookId++;
        }
        return bookId;
    }

    @Override
    public List<Book> getAll() {

        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Book> findByTitle(String title) {

        return storage.values().stream().filter(elem ->
                        elem.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long bookId) {
        Book book = storage.get(bookId);
        storage.remove(book.getBookId());
    }

    @Override
    public Optional<Book> findById(Long bookId) {
        return Optional.ofNullable(storage.get(bookId));
    }

    @Override
    public void add(Book book) {
        storage.put(book.getBookId(), book);
    }

    @Override
    public int getSize() {
        return this.storage.size();
    }
}
