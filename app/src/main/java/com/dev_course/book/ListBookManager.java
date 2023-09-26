package com.dev_course.book;

import java.util.List;
import java.util.stream.Collectors;

import static com.dev_course.book.BookManagerMessage.*;

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
    public String deleteById(int id) {
        if (!hasId(id)) {
            return NOT_EXIST_ID.msg();
        }

        bookList.removeIf(book -> book.getId() == id);

        return SUCCESS_DELETE_BOOK.msg();
    }

    private boolean hasId(int id) {
        return bookList.stream()
                .anyMatch(book -> book.getId() == id);
    }

    private boolean hasTitle(String title) {
        return bookList.stream()
                .anyMatch(book -> book.getTitle().equals(title));
    }
}
