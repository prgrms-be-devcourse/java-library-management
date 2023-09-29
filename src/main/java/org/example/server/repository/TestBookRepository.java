package org.example.server.repository;

import org.example.server.entity.Book;
import org.example.server.exception.BookNotFoundException;
import org.example.server.exception.EmptyLibraryException;

import java.util.LinkedHashMap;
import java.util.Optional;

public class TestBookRepository implements BookRepository {
    private static int count;
    private static LinkedHashMap<Integer, Book> data;

    public TestBookRepository() {
        loadData();
    }

    @Override
    public void loadData() {
        count = 0;
        data = new LinkedHashMap<>();
    }

    @Override
    public void create(Book book) {
        int bookId = count++;
        book.id = bookId;
        data.put(bookId, book);
    }

    @Override
    public String readAll() {
        if (data.isEmpty())
            throw new EmptyLibraryException();
        StringBuilder sb = new StringBuilder();
        data.values().forEach((book) -> {
            sb.append(book.toString());
        });
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public String searchByName(String bookName) {
        StringBuilder sb = new StringBuilder();
        data.values().stream().filter(book -> book.name.contains(bookName)).forEach(sb::append);
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
        return book.get();
    }

    @Override
    public void delete(int bookId) {
        data.remove(bookId);
    }
}
