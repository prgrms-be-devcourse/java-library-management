package com.libraryManagement.model;

import com.libraryManagement.model.domain.Book;

public class BookDTO{
    private final long id;
    private final String title;
    private final String author;
    private final int pages;

    public BookDTO(long id, String title, String author, int pages) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPages() {
        return pages;
    }
}
