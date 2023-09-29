package com.dev_course.book;

import java.util.List;
import java.util.stream.Collectors;

import static com.dev_course.book.BookManagerMessage.*;
import static com.dev_course.book.BookState.*;

public class ListBookManager implements BookManager {
    private final List<Book> bookList;
    private int id;

    public ListBookManager(List<Book> bookList, int seed) {
        this.bookList = bookList;
        this.id = seed;
    }

    @Override
    public String create(String title, String author, int pages) {
        if (hasTitle(title)) {
            return ALREADY_EXIST_TITLE.msg();
        }

        Book newBook = new Book(++id, title, author, pages);

        bookList.add(newBook);

        return SUCCESS_CREATE_BOOK.msg();
    }

    @Override
    public String getInfo() {
        return bookList.stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String getInfoByTitle(String title) {
        return bookList.stream()
                .filter(book -> book.getTitle().contains(title))
                .map(Book::toString)
                .collect(Collectors.joining("\n"));
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
            return "%s (%s)\n".formatted(FAIL_RENT_BOOK.msg(), target.getState().label());
        }

        target.setState(LOAN);

        return SUCCESS_RENT_BOOK.msg();
    }

    @Override
    public String lossById(int id) {
        return null;
    }

    @Override
    public String returnById(int id) {
        return null;
    }

    @Override
    public String deleteById(int id) {
        if (hasNotId(id)) {
            return NOT_EXIST_ID.msg();
        }

        bookList.removeIf(book -> book.getId() == id);

        return SUCCESS_DELETE_BOOK.msg();
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
