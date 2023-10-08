package org.example.packet.responsePacket;

import org.example.server.entity.Book;

public class BookResponseDto {
    public final int id;
    public final String name;
    public final String author;
    public final int pages;
    public String status;

    public BookResponseDto(Book book) {
        this.id = book.id;
        this.name = book.name;
        this.author = book.author;
        this.pages = book.pages;
        this.status = book.status.getType().nameKor;
    }
}
