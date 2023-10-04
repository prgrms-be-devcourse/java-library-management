package org.example.packet.requestPacket;

import org.example.packet.BookDto;
import org.example.packet.MethodType;

public class RequestWithBook extends RequestPacket {
    private final BookDto bookDto;

    public RequestWithBook(MethodType method, BookDto bookDto) {
        super(method);
        this.bookDto = bookDto;
    }

    public BookDto getBookDto() {
        return bookDto;
    }

    @Override
    public String toString() {
        return "RequestWithBook{" +
                "bookDto=" + bookDto +
                '}';
    }
}
