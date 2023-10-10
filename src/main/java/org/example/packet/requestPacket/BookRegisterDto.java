package org.example.packet.requestPacket;

public class BookRegisterDto {
    public final String name;
    public final String author;
    public final int pages;

    public BookRegisterDto(String name, String author, int pages) {
        this.name = name;
        this.author = author;
        this.pages = pages;
    }
}
