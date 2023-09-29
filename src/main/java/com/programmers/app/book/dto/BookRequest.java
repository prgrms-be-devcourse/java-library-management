package com.programmers.app.book.dto;

import com.programmers.app.book.domain.Book;

public class BookRequest {
    private String title;
    private String author;
    private int totalPages;

    public BookRequest(String title, String author, int totalPages) {
        this.title = title;
        this.author = author;
        this.totalPages = totalPages;
    }

    public Book toBook(long bookNumber) {
        return new Book(bookNumber, title, author, totalPages);
    }
}
