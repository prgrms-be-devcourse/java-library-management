package org.example.server.repository;

import org.example.server.entity.Book;
import org.example.server.exception.BookNotFoundException;

import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRepository implements Repository {
    public final ConcurrentHashMap<Integer, Book> data = new ConcurrentHashMap<>();
    private final TimeChecker timeChecker = new TimeChecker();
    public int newId = 1;

    @Override
    public void save(Book book) {
        int bookId = newId++;
        book.id = bookId;
        data.put(bookId, book);
    }

    @Override
    public LinkedList<Book> findAll() {
        LinkedList<Book> books = new LinkedList<>();
        data.values().forEach((book) -> books.add(timeChecker.checkLoadTime(book)));
        return books;
    }

    @Override
    public LinkedList<Book> findAllByName(String name) {
        LinkedList<Book> books = new LinkedList<>();
        data.values().forEach(
                book -> {
                    timeChecker.checkLoadTime(book);
                    if (book.name.contains(name)) books.add(book);
                }
        );
        return books;
    }

    @Override
    public Book getById(int id) {
        Optional<Book> book = Optional.ofNullable(data.get(id));
        if (book.isEmpty())
            throw new BookNotFoundException();
        return timeChecker.checkLoadTime(book.get());
    }

    @Override
    public void delete(int id) {
        data.remove(id);
    }
}
