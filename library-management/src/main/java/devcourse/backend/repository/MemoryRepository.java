package devcourse.backend.repository;

import devcourse.backend.medel.Book;
import devcourse.backend.medel.BookStatus;

import java.util.ArrayList;
import java.util.List;

public class MemoryRepository implements Repository {
    List<Book> books = new ArrayList<>();

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
    public Book findById(long id) {
        return books.stream()
                .filter(b -> b.getId() == id)
                .findAny().orElseThrow(() -> new IllegalArgumentException("존재하지 않는 도서번호 입니다."));
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
