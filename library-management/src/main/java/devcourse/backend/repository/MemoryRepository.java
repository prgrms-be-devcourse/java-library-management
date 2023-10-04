package devcourse.backend.repository;

import devcourse.backend.medel.Book;
import devcourse.backend.medel.BookStatus;

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
                .map(b -> b.copy())
                .sorted((a, b) -> Math.toIntExact(a.getId() - b.getId()))
                .toList();
    }

    @Override
    public List<Book> findByKeyword(String keyword) {
        return books.stream()
                .filter(b -> b.like(keyword))
                .map(b -> b.copy())
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
    public void changeStatus(long id, BookStatus status) {
        findById(id).changeStatus(status);
    }

    @Override
    public void deleteById(long bookId) {
        books.remove(findById(bookId));
    }
}
