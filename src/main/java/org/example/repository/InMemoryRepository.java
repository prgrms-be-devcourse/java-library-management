package org.example.repository;

import org.example.domain.Book;
import org.example.domain.BookStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryRepository implements Repository {
    List<Book> books = new ArrayList<>();

    @Override
    public void saveBook(Book book) {
        books.add(book);
    }

    @Override
    public List<Book> findAllBooks() {
        return books;
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findBookById(Integer bookId) {
        return books.stream()
                .filter(book -> book.getId().equals(bookId))
                .findFirst();
    }

    @Override
    public void updateBookStatus(Integer bookId, BookStatus status) {
        Optional<Book> bookToUpdate = books.stream()
                .filter(book -> book.getId().equals(bookId))
                .findFirst();
        bookToUpdate.ifPresent(
                book -> {
                    book.setStatus(status);
                    if (status == BookStatus.ORGANIZING)
                        book.setReturnTime(Instant.now());
                    else
                        book.setReturnTime(null);
                }
        );
    }

    @Override
    public void deleteBookById(Integer bookId) {
        books.removeIf(book -> book.getId().equals(bookId));
    }

    @Override
    public Integer getNextBookId() {
        Integer lastBookId = 0;
        if (books.size() > 0) {
            lastBookId = books.get(books.size() - 1).getId();
        }

        return lastBookId + 1;
    }
}
