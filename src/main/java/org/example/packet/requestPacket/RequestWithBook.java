package org.example.packet.requestPacket;

import org.example.packet.BookRegisterDto;

public class RequestWithBook extends RequestPacket {
    public final BookRegisterDto bookInfo;

    public RequestWithBook(String method, BookRegisterDto bookDto) {
        super(method);
        this.bookInfo = bookDto;
    }

    @Override
    public String toString() {
        return "RequestWithBook{" +
                "bookDto=" + bookInfo +
                '}';
    }
}
