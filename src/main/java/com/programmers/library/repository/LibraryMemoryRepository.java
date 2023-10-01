package com.programmers.library.repository;

import com.programmers.library.domain.Book;
import com.programmers.library.utils.StatusType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LibraryMemoryRepository implements LibraryRepository{
    private static List<Book> books = new ArrayList<>();
    private static int sequence = 0;

    @Override
    public int save(Book book) {
        book.setBookId(++sequence);
        books.add(book);
        return book.getBookId();
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books);
    }

    @Override
    public List<Book> findByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findById(int bookId) {
        return books.stream()
                .filter(book -> book.getBookId() == bookId)
                .findFirst();
    }


    @Override
    public void delete(int bookId) {
        books.removeIf(book -> book.getBookId() == bookId);
    }

    @Override
    public void updateStatus(int bookId, StatusType status) {
        Book book = findById(bookId).get();
        book.setStatus(status);
    }

    @Override
    public void clearAll() {
        books.clear();
    }
}
