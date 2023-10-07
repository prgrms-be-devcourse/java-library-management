package org.example.packet;

import org.example.server.entity.Book;

public class BookResponseDto { // ++ 주고받는 형식, 프로토콜이 변경되더라도 유연하게 할 수 있도록(선택, 한번만 고민)
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
