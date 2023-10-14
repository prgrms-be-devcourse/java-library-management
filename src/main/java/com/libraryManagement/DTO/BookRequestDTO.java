package com.libraryManagement.DTO;

public class BookRequestDTO {
    private final String title;
    private final String author;
    private final int pages;

    public BookRequestDTO(String title, String author, int pages) {
        this.title = title;
        this.author = author;
        this.pages = pages;
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
