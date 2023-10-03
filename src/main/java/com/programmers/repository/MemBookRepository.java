package com.programmers.repository;

import com.programmers.domain.Book;
import com.programmers.domain.BookState;
import com.programmers.provider.BookIdProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemBookRepository implements BookRepository {
    private static final List<Book> books = new ArrayList<>();

    private MemBookRepository() {
        BookIdProvider.initMaxId(books);
    }

    private static class Holder {
        private static final MemBookRepository INSTANCE = new MemBookRepository();
    }

    public static MemBookRepository getInstance() {
        return MemBookRepository.Holder.INSTANCE;
    }

    @Override
    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return books;
    }

    @Override
    public Optional<Book> findBookById(int id) {
        return books.stream()
                .filter(book -> book.isSameId(id))
                .findFirst();
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        return books.stream()
                .filter(book -> book.containsInTitle(title))
                .toList();
    }

    @Override
    public void updateBookState(Book book, BookState bookState) {
        if (bookState == BookState.AVAILABLE) {
            book.setToAvailable();
        } else if (bookState == BookState.RENTED) {
            book.setToRented();
        } else if (bookState == BookState.LOST) {
            book.setToLost();
        } else if (bookState == BookState.ORGANIZING) {
            book.setToOrganizing();
        }
    }

    @Override
    public void deleteBook(Book book) {
        books.remove(book);
    }

    @Override
    public void clearBooks() {
        books.clear();
    }
}
