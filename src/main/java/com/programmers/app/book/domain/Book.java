package com.programmers.app.book.domain;

public class Book {
    private long bookNumber;
    private String title;
    private String author;
    private int totalPages;
    private String status; //Enum considerable

    public Book(long bookNumber, String title, String author, int totalPages) {
        this.bookNumber = bookNumber;
        this.title = title;
        this.author = author;
        this.totalPages = totalPages;
        this.status = "대여 가능";
    }

    public long getBookNumber() {
        return bookNumber;
    }
}
