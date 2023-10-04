package devcourse.backend.repository;

import devcourse.backend.model.Book;
import devcourse.backend.model.BookStatus;

import java.util.*;

public class MemoryRepository implements Repository {
    private Map<Long, Book> books;

    public List<Book> getBooksASList() {
        return books.values().stream().toList();
    }

    public void setBooks(List<Book> books) {
        this.books = new HashMap<>();
        books.stream().forEach(b -> this.books.put(b.getId(), b));
    }

    @Override
    public List<Book> findAll() {
        return books.values()
                .stream()
                .sorted((a, b) -> Math.toIntExact(a.getId() - b.getId()))
                .toList();
    }

    @Override
    public List<Book> findByKeyword(String keyword) {
        return books.values()
                .stream()
                .filter(b -> b.like(keyword))
                .sorted((a, b) -> Math.toIntExact(a.getId() - b.getId()))
                .toList();
    }

    @Override
    public Optional<Book> findById(long id) {
        if(books.containsKey(id)) return Optional.of(books.get(id));
        return Optional.empty();
    }

    @Override
    public void deleteById(long bookId) {
        books.remove(bookId);
    }

    @Override
    public void addBook(Book book) {
        books.put(book.getId(), book);
    }

    @Override
    public void flush() {
        // DO NOTHING
    }
}
