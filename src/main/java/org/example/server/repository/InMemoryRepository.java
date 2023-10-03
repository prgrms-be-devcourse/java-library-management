package org.example.server.repository;

import org.example.server.entity.Book;
import org.example.server.exception.BookNotFoundException;
import org.example.server.exception.EmptyLibraryException;

import java.util.LinkedHashMap;
import java.util.Optional;

public class InMemoryRepository implements Repository {
    public final LinkedHashMap<Integer, Book> data = new LinkedHashMap<>();
    public int newId = 1;

    @Override
    public void create(Book book) {
        int bookId = newId++;
        book.id = bookId;
        data.put(bookId, book);
    }

    @Override
    public String readAll() {
        if (data.isEmpty())
            throw new EmptyLibraryException();
        StringBuilder sb = new StringBuilder();
        data.values().forEach((book) -> {
            sb.append(checkLoadTime(book));
        });
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public String searchByName(String bookName) {
        StringBuilder sb = new StringBuilder();
        data.values().forEach(
                book -> {
                    checkLoadTime(book);
                    if (book.name.contains(bookName)) sb.append(book);
                }
        );
        if (sb.isEmpty())
            throw new EmptyLibraryException();
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public Book getById(int bookId) {
        Optional<Book> book = Optional.ofNullable(data.get(bookId));
        if (book.isEmpty())
            throw new BookNotFoundException();
        return checkLoadTime(book.get());
    }

    @Override
    public void delete(int bookId) {
        data.remove(bookId);
    }

    @Override
    public void save() {
    }
}
