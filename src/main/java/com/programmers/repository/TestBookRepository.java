package com.programmers.repository;

import com.programmers.cons.Const;
import com.programmers.domain.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TestBookRepository implements BookRepository{

    private final List<Book> bookList = new ArrayList<>();

    @Override
    public List<Book> findByBookTitle(String title) {
        return bookList.stream()
                .filter((book) -> book.searchByTitle(title))
                .toList();
    }

    @Override
    public Optional<Book> findByBookId(Long bookId) {
        return bookList.stream()
                .filter(book -> book.filterById(bookId))
                .findAny();
    }

    @Override
    public List<Book> findAll() {
        return bookList;
    }

    @Override
    public void saveBook(Book book) {
        bookList.add(book);
    }

    @Override
    public void clear() {
        bookList.clear();
        Const.resetSequence();
    }

}
