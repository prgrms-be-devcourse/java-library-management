package com.programmers.app.book.dto;

import com.programmers.app.book.domain.Book;
import com.programmers.app.book.domain.BookStatus;

public class BookRequest {
    private final String title;
    private final String author;
    private final int totalPages;

    public BookRequest(String title, String author, int totalPages) {
        this.title = title;
        this.author = author;
        this.totalPages = totalPages;
    }

    public Book toBook(int bookNumber) {
        return new Book(bookNumber, title, author, totalPages, BookStatus.IN_PLACE, null);
    }
}
