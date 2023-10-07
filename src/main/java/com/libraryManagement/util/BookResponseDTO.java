package com.libraryManagement.util;

public class BookResponseDTO {
    private final String title;
    private final String author;
    private final int pages;

    public BookResponseDTO(String title, String author, int pages) {
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    public BookResponseDTO(BookRequestDTO bookRequestDTO) {
        this.title = bookRequestDTO.getTitle();
        this.author = bookRequestDTO.getAuthor();
        this.pages = bookRequestDTO.getPages();
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
