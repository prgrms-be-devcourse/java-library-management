package org.example.packet;

public class BookRegisterDto { // ++ 주고받는 형식, 프로토콜이 변경되더라도 유연하게 할 수 있도록(선택, 한번만 고민)
    public final String name;
    public final String author;
    public final int pages;

    public BookRegisterDto(String name, String author, int pages) {
        this.name = name;
        this.author = author;
        this.pages = pages;
    }
}
