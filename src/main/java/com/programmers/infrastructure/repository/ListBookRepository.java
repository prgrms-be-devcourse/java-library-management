package com.programmers.infrastructure.repository;

import com.programmers.domain.entity.Book;
import com.programmers.domain.repository.BookRepository;

import java.util.*;
public class ListBookRepository implements BookRepository {
    private final List<Book> books = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Book save(Book book) {
        books.add(book);
        return book;
    }

    @Override
    public Optional<Book> findById(String id) {
        return books.stream().filter(book -> Objects.equals(book.getId(), id)).findAny();
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books);
    }

    @Override
    public void deleteById(String id) {
        books.removeIf(book -> Objects.equals(book.getId(), id));
    }

    @Override
    public void update(Book updatedBook) {
        books.removeIf(book -> Objects.equals(book.getId(), updatedBook.getId()));
        books.add(updatedBook);
    }

    @Override
    public List<Book> findByTitle(String title) {
        return null;
    }
}

