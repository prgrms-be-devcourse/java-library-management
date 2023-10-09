package devcourse.backend.repository;

import devcourse.backend.model.Book;
import devcourse.backend.model.BookStatus;

import java.util.*;

public class MemoryRepository implements Repository {
    private Map<Long, Book> books;

    public MemoryRepository() {
        this.books = new HashMap<>();
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
    public Optional<Book> findByTitleAndAuthor(String title, String author) {
        return books.values().stream()
                .filter(b -> b.getTitle().equals(title))
                .filter(b -> b.getAuthor().equals(author))
                .findAny();
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
    public void save() {
        // Memory Repository는 데이터를 스토리지에 저장하지 않습니다 (휘발성)
    }
}
