package org.example.packet.responsePacket;

import java.util.LinkedList;

public class ResponseSuccessWithData extends ResponsePacket {

    public final LinkedList<BookResponseDto> books;

    public ResponseSuccessWithData(String method, LinkedList<BookResponseDto> bookDtos) {
        super(method);
        this.books = bookDtos;
    }

    @Override
    public String toString() {
        return "ResponseSuccessWithData{" +
                "bookDtos=" + books +
                '}';
    }
}
