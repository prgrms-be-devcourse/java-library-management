package com.programmers.app.book.request;

import com.programmers.app.book.domain.Book;

public class RequestBook {
    private String title;
    private String author;
    private int totalPages;

    public RequestBook(String title, String author, int totalPages) {
        this.title = title;
        this.author = author;
        this.totalPages = totalPages;
    }

    public Book toBook(long bookNumber) {
        return new Book(bookNumber, title, author, totalPages);
    }
}
