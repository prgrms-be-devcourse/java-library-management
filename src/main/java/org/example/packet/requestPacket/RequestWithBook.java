package org.example.packet.requestPacket;

import org.example.packet.dto.BookDto;

public class RequestWithBook extends RequestPacket {
    public final BookDto BOOK_INFO;

    public RequestWithBook(String method, BookDto bookDto) {
        super(method);
        this.BOOK_INFO = bookDto;
    }

    @Override
    public String toString() {
        return "RequestWithBook{" +
                "bookDto=" + BOOK_INFO +
                '}';
    }
}
