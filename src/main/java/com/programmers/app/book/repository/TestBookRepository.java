package com.programmers.app.book.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.programmers.app.book.domain.Book;

public class TestBookRepository implements BookRepository {
    private Map<Long, Book> books;

    public TestBookRepository () {
        books = new HashMap<>();
    }

    @Override
    public void add(Book book) {
        books.put(book.getBookNumber(), book);
    }

    @Override
    public long getLastBookNumber() {
        return Collections.max(books.keySet());
    }

    @Override
    public Map<Integer, Book> findAllBooks() {
        return null;
    }

    @Override
    public Book findByTitle(String title) {
        return null;
    }

    @Override
    public Book findByBookNumber(long bookNumber) {
        return null;
    }

    @Override
    public void save(Book book) {

    }

    @Override
    public void deleteByBookNumber(long bookNumber) {

    }

    @Override
    public void saveFile() {

    }
}
