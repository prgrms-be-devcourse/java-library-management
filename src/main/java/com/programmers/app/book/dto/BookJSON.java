package com.programmers.app.book.dto;

import java.time.LocalDateTime;

import com.programmers.app.book.domain.Book;
import com.programmers.app.book.domain.BookStatus;

public class BookJSON {

    private final int bookNumber;
    private final String title;
    private final String author;
    private final int totalPages;
    private final BookStatus status;
    private final String arrangementBegunAt;

    public BookJSON(
            int bookNumber,
            String title,
            String author,
            int totalPages,
            BookStatus status,
            LocalDateTime arrangementBegunAt) {
        this.bookNumber = bookNumber;
        this.title = title;
        this.author = author;
        this.totalPages = totalPages;
        this.status = status;
        this.arrangementBegunAt = arrangementBegunAt == null ? null : arrangementBegunAt.toString();
    }

    public Book toBook() {
        return new Book(
                bookNumber,
                title,
                author,
                totalPages,
                status,
                arrangementBegunAt == null ? null : LocalDateTime.parse(arrangementBegunAt));
    }

    public int getBookNumber() {
        return bookNumber;
    }
}
