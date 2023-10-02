package org.example.repository;

import org.example.domain.Book;
import org.example.domain.BookStatusType;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryRepository implements Repository {
    private Map<Integer, Book> books = new HashMap<>();

    @Override
    public void saveBook(Book book) {
        books.put(book.getId(), book);
    }

    @Override
    public List<Book> findAllBooks() {
        return new ArrayList<>(books.values());
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        return books.values().stream()
                .filter(book -> book.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findBookById(Integer bookId) {
        return Optional.ofNullable(books.get(bookId));
    }

    @Override
    public void updateBookStatus(Integer bookId, BookStatusType status) {
        Book bookToUpdate = books.get(bookId);
        bookToUpdate.setStatus(status);
        if (BookStatusType.ORGANIZING == status) {
            bookToUpdate.setReturnTime(LocalDateTime.now());
        } else {
            bookToUpdate.setReturnTime(null);
        }
    }

    @Override
    public void deleteBookById(Integer bookId) {
        books.remove(bookId);
    }

    @Override
    public Integer getNextBookId() {
        return books.keySet().stream().max(Integer::compareTo).orElse(1);
    }
}
