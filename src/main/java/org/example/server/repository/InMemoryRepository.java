package org.example.server.repository;

import org.example.server.entity.Book;
import org.example.server.exception.BookNotFoundException;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Optional;

public class InMemoryRepository implements Repository {
    public final LinkedHashMap<Integer, Book> data = new LinkedHashMap<>();
    public int newId = 1;

    @Override
    public void save(Book book) {
        int bookId = newId++;
        book.id = bookId;
        data.put(bookId, book);
    }

    @Override
    public LinkedList<Book> getAll() {
        LinkedList<Book> books = new LinkedList<>();
        data.values().forEach((book) -> {
            books.add(checkLoadTime(book));
        });
        return books;
    }

    @Override
    public LinkedList<Book> getByName(String name) {
        LinkedList<Book> books = new LinkedList<>();
        data.values().forEach(
                book -> {
                    checkLoadTime(book);
                    if (book.name.contains(name)) books.add(book);
                }
        );
        return books;
    }

    @Override
    public Book findById(int id) {
        Optional<Book> book = Optional.ofNullable(data.get(id));
        if (book.isEmpty())
            throw new BookNotFoundException();
        return checkLoadTime(book.get());
    }

    @Override
    public void delete(int id) {
        data.remove(id);
    }
}
