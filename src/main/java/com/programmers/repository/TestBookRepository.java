package com.programmers.repository;

import com.programmers.domain.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TestBookRepository implements BookRepository{

    private final List<Book> bookList = new ArrayList<>();
    private static long sequence = 0L; //static

    @Override
    public List<Book> findByBookTitle(String title) {
        return bookList.stream()
                .filter((book) -> book.searchByTitle(title))
                .toList();
    }

    @Override
    public Optional<Book> findByBookId(Long bookId) {
        return bookList.stream()
                .filter((book) -> Objects.equals(book.getBookId(), bookId))
                .findAny();
    }

    @Override
    public List<Book> findAll() {
        return bookList;
    }

    @Override
    public void saveBook(Book book) {
        book.settingId(++sequence);
        bookList.add(book);
    }

    @Override
    public void clear() {
        bookList.clear();
        sequence = 0L;
    }

}
