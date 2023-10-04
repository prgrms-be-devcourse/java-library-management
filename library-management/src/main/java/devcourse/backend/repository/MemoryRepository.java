package devcourse.backend.repository;

import devcourse.backend.model.Book;
import devcourse.backend.model.BookStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryRepository implements Repository {
    List<Book> books = new ArrayList<>();

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public List<Book> findAll() {
        return books.stream()
                .sorted((a, b) -> Math.toIntExact(a.getId() - b.getId()))
                .toList();
    }

    @Override
    public List<Book> findByKeyword(String keyword) {
        return books.stream()
                .filter(b -> b.like(keyword))
                .sorted((a, b) -> Math.toIntExact(a.getId() - b.getId()))
                .toList();
    }

    @Override
    public Optional<Book> findById(long id) {
        return books.stream()
                .filter(b -> b.getId() == id)
                .findAny();
    }

    @Override
    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public void deleteById(long bookId) {
        books.remove(findById(bookId));
    }

    @Override
    public void flush() {
        // DO NOTHING
    }
}
