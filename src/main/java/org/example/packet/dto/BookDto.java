package org.example.packet.dto;

import org.example.server.entity.Book;

public class BookDto {
    public int id; // 5000 미만
    public String name; // 100자 미만
    public String author; // 100자 미만
    public int pages; // 5000 미만
    public String status;

    public BookDto(Book book) {
        this.id = book.id;
        this.name = book.name;
        this.author = book.author;
        this.pages = book.pages;
        this.status = book.status.getBookStatusType().getStatusName();
    }

    public BookDto(String name, String author, int pages) {
        this.name = name;
        this.author = author;
        this.pages = pages;
    }
}
