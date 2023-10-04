package org.example.packet;

import org.example.server.entity.Book;

public class BookDto {
    public final String NAME; // 100자 미만
    public final String AUTHOR; // 100자 미만
    public final int PAGES; // 5000 미만
    public int id; // 5000 미만
    public String status;

    public BookDto(Book book) {
        this.id = book.id;
        this.NAME = book.name;
        this.AUTHOR = book.author;
        this.PAGES = book.pages;
        this.status = book.status.getType().NAME_KOR;
    }

    public BookDto(String NAME, String author, int pages) {
        this.NAME = NAME;
        this.AUTHOR = author;
        this.PAGES = pages;
    }
}
