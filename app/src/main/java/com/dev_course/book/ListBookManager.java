package com.dev_course.book;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.dev_course.book.BookManagerMessage.*;
import static com.dev_course.book.BookState.*;

public class ListBookManager implements BookManager {
    private static final int PROCESSING_COST = 300_000;
    private final List<Book> bookList = new ArrayList<>();
    private final String infoDelim = "\n------------------------------\n";
    private int id;

    @Override
    public void init(Collection<Book> data) {
        bookList.addAll(data);

        id = bookList.stream()
                .mapToInt(Book::getId)
                .max()
                .orElse(0);
    }

    @Override
    public void updateStates() {
        long currentTime = getCurrentTime();
        long processedTime = currentTime - PROCESSING_COST;

        bookList.stream()
                .filter(book -> isProcessed(book, processedTime))
                .forEach(book -> {
                    book.setState(AVAILABLE);
                    book.setUpdateAt(currentTime);
                });
    }

    @Override
    public String create(String title, String author, int pages) {
        if (hasTitle(title)) {
            return ALREADY_EXIST_TITLE.msg();
        }

        Book newBook = new Book(++id, title, author, pages, getCurrentTime());

        bookList.add(newBook);

        return SUCCESS_CREATE_BOOK.msg();
    }

    @Override
    public String getInfo() {
        return bookList.stream()
                .map(Book::toString)
                .collect(Collectors.joining(infoDelim));
    }

    @Override
    public String getInfoByTitle(String title) {
        return bookList.stream()
                .filter(book -> book.getTitle().contains(title))
                .map(Book::toString)
                .collect(Collectors.joining(infoDelim));
    }

    @Override
    public String rentById(int id) {
        if (hasNotId(id)) {
            return NOT_EXIST_ID.msg();
        }

        Book target = bookList.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow();

        if (target.getState() != AVAILABLE) {
            return "%s (%s)".formatted(FAIL_RENT_BOOK.msg(), target.getState().label());
        }

        target.setState(LOAN);
        target.setUpdateAt(getCurrentTime());

        return SUCCESS_RENT_BOOK.msg();
    }

    @Override
    public String returnById(int id) {
        if (hasNotId(id)) {
            return NOT_EXIST_ID.msg();
        }

        Book target = bookList.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow();

        BookState state = target.getState();

        if (!state.isReturnable()) {
            return FAIL_RETURN_BOOK.msg();
        }

        target.setState(PROCESSING);
        target.setUpdateAt(getCurrentTime());

        return SUCCESS_RETURN_BOOK.msg();
    }

    @Override
    public String lossById(int id) {
        if (hasNotId(id)) {
            return NOT_EXIST_ID.msg();
        }

        Book target = bookList.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow();

        if (target.getState() == LOST) {
            return ALREADY_LOST_BOOK.msg();
        }

        target.setState(LOST);
        target.setUpdateAt(getCurrentTime());

        return SUCCESS_LOSS_BOOK.msg();
    }

    @Override
    public String deleteById(int id) {
        if (hasNotId(id)) {
            return NOT_EXIST_ID.msg();
        }

        bookList.removeIf(book -> book.getId() == id);

        return SUCCESS_DELETE_BOOK.msg();
    }

    @Override
    public List<Book> getBookList() {
        return bookList.stream().toList();
    }

    private long getCurrentTime() {
        return System.currentTimeMillis();
    }

    private boolean isProcessed(Book book, long processedTime) {
        return book.getState() == PROCESSING && book.getUpdateAt() <= processedTime;
    }

    private boolean hasNotId(int id) {
        return bookList.stream()
                .noneMatch(book -> book.getId() == id);
    }

    private boolean hasTitle(String title) {
        return bookList.stream()
                .anyMatch(book -> book.getTitle().equals(title));
    }
}
