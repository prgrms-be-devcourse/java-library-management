package org.example.server.repository;

import org.example.server.entity.Book;
import org.example.server.exception.BookNotFoundException;

import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRepository implements Repository {
    public final ConcurrentHashMap<Integer, Book> DATA = new ConcurrentHashMap<>();
    private final TimeChecker TIME_CHECKER = new TimeChecker();
    public int newId = 1;

    @Override
    public void save(Book book) {
        int bookId = newId++;
        book.id = bookId;
        DATA.put(bookId, book);
    }

    @Override
    public LinkedList<Book> getAll() {
        LinkedList<Book> books = new LinkedList<>();
        DATA.values().forEach((book) -> {
            books.add(TIME_CHECKER.checkLoadTime(book));
        });
        return books;
    }

    @Override
    public LinkedList<Book> getByName(String name) { // 여러개가
        LinkedList<Book> books = new LinkedList<>();
        DATA.values().forEach(
                book -> {
                    TIME_CHECKER.checkLoadTime(book);
                    if (book.name.contains(name)) books.add(book);
                }
        );
        return books;
    }
//get : null일 수 없다 예외
//find : null일 수 있다. Optional로 반환 -> 컨벤션 JPA
    @Override
    public Book findById(int id) {
        Optional<Book> book = Optional.ofNullable(DATA.get(id));
        if (book.isEmpty())
            throw new BookNotFoundException();
        return TIME_CHECKER.checkLoadTime(book.get());
    }

    @Override
    public void delete(int id) {
        DATA.remove(id);
    }
}
