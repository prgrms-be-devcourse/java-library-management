package com.library.java_library_management.dto;

import com.library.java_library_management.status.BookStatus;

public class JsonInfo {
    private int book_id;
    private String author;
    private String title;
    private int page_size;
    private String status;
    public JsonInfo(int book_id, String author, String title, int page_size, String status) {
        this.book_id = book_id;
        this.author = author;
        this.title = title;
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

    public String getStatus() {
        return status;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
