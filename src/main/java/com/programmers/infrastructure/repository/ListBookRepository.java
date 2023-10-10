package com.programmers.infrastructure.repository;

import com.programmers.domain.entity.Book;
import com.programmers.domain.repository.BookRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
public class ListBookRepository implements BookRepository {
    private final List<Book> books = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Book save(Book book) {
        books.add(book);
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return books.stream().filter(book -> Objects.equals(book.getId(), id)).findAny();
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books);
    }

    @Override
    public int deleteById(Long id) {
        return books.removeIf(book -> Objects.equals(book.getId(), id)) ? 1 : 0;
    }

    @Override
    public int update(Book updatedBook) {
        var result = books.removeIf(book -> Objects.equals(book.getId(), updatedBook.getId()));
        books.add(updatedBook);
        return result ? 1 : 0;
    }

    @Override
    public List<Book> findByTitle(String title) {
        return books.stream().filter(book -> book.getTitle().contains(title)).toList();
    }
}

