package com.library.java_library_management.dto;

public class JsonInfo {
    private final int book_id;
    private final String author;
    private final String title;
    private final int page_size;
    private String status;

    public JsonInfo(int book_id, String author, String title, int page_size, String status) {
        this.book_id = book_id;
        this.author = author;
        this.title = title;
        this.page_size = page_size;
        this.status = status;
    }

}
