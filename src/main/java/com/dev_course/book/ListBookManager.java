package com.dev_course.book;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.dev_course.book.BookState.*;

public class ListBookManager implements BookManager {
    private final Duration PROCESSING_COST = Duration.ofMinutes(5);
    private final List<Book> books = new ArrayList<>();

    private int id = 0;

    @Override
    public void init(Collection<Book> data) {
        books.addAll(data);

        id = books.stream()
                .mapToInt(Book::getId)
                .max()
                .orElse(0);
    }

    @Override
    public void updateStates() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime processedTime = currentTime.minus(PROCESSING_COST);

        books.stream()
                .filter(book -> book.isProcessed(processedTime))
                .forEach(book -> {
                    book.setState(AVAILABLE);
                    book.setUpdateAt(currentTime);
                });
    }

    @Override
    public boolean create(String title, String author, int pages) {
        if (hasTitle(title)) {
            return false;
        }

        Book newBook = new Book(++id, title, author, pages, LocalDateTime.now());

        books.add(newBook);

        return true;
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().contains(title))
                .toList();
    }

    @Override
    public boolean rentById(int id) {
        Book target = books.stream()
                .filter(book -> book.isSame(id))
                .findFirst()
                .orElseThrow();

        BookState state = target.getState();

        if (!state.isRentable()) {
            return false;
        }

        target.setState(LOAN);
        target.setUpdateAt(LocalDateTime.now());

        return true;
    }

    @Override
    public boolean returnById(int id) {
        Book target = books.stream()
                .filter(book -> book.isSame(id))
                .findFirst()
                .orElseThrow();

        BookState state = target.getState();

        if (!state.isReturnable()) {
            return false;
        }

        target.setState(PROCESSING);
        target.setUpdateAt(LocalDateTime.now());

        return true;
    }

    @Override
    public boolean lossById(int id) {
        Book target = books.stream()
                .filter(book -> book.isSame(id))
                .findFirst()
                .orElseThrow();

        if (target.getState() == LOST) {
            return false;
        }

        target.setState(LOST);
        target.setUpdateAt(LocalDateTime.now());

        return true;
    }

    @Override
    public boolean deleteById(int id) {
        return books.removeIf(book -> book.isSame(id));
    }

    @Override
    public List<Book> getBooks() {
        return books;
    }

    private boolean hasTitle(String title) {
        return books.stream()
                .anyMatch(book -> book.getTitle().equals(title));
    }
}
