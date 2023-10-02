package com.library.java_library_management.dto;

import com.library.java_library_management.status.BookStatus;

public class BookInfo {
    private final int book_id;
    private final String title;
    private final String author;
    private final int page_size;
    private BookStatus status;
    public BookInfo(int book_id, String title, String author, int page_size, BookStatus status) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.page_size = page_size;
        this.status = status;
    }

    public int getBook_id() {
        return book_id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public int getPage_size() {
        return page_size;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }
}
