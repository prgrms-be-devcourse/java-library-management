package library.repository;

import library.domain.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryBookRepository implements BookRepository {
    private final List<Book> bookList;

    public InMemoryBookRepository() {
        this.bookList = new ArrayList<>();
    }

    @Override
    public void add(Book item) {
        bookList.add(item);
    }

    @Override
    public Optional<Book> findByBookNumber(long bookNumber) {
        return bookList.stream()
                .filter(item -> item.equalsBookNumber(bookNumber))
                .findFirst();
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(this.bookList);
    }

    @Override
    public List<Book> findListContainTitle(String title) {
        return bookList.stream()
                .filter(item -> item.containsTitle(title))
                .toList();
    }

    @Override
    public void delete(Book book) {
        bookList.remove(book);
    }

    @Override
    public void persist() {
        // do nothing
    }

    @Override
    public long getNextBookNumber() {
        return bookList.isEmpty()
                ? 1
                : bookList.get(bookList.size() - 1).getBookNumber() + 1;
    }
}
