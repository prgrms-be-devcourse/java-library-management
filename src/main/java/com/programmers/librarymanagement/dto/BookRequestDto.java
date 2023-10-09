package com.programmers.librarymanagement.dto;

public class BookRequestDto {

    private final String title;

    private final String author;

    private final int page;

    public BookRequestDto(String title, String author, int page) {
        this.title = title;
        this.author = author;
        this.page = page;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPage() {
        return page;
    }
}
