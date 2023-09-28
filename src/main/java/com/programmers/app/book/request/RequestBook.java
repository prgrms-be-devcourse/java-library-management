package com.programmers.app.book.request;

public class RequestBook {
    private String title;
    private String author;
    private int totalPages;

    public RequestBook(String title, String author, int totalPages) {
        this.title = title;
        this.author = author;
        this.totalPages = totalPages;
    }
}
