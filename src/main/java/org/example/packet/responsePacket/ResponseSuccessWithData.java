package org.example.packet.responsePacket;

import org.example.packet.dto.BookDto;

import java.util.LinkedList;

public class ResponseSuccessWithData extends ResponsePacket {

    public final LinkedList<BookDto> BOOKS;

    public ResponseSuccessWithData(String method, LinkedList<BookDto> bookDtos) {
        super(method);
        this.BOOKS = bookDtos;
    }

    @Override
    public String toString() {
        return "ResponseSuccessWithData{" +
                "bookDtos=" + BOOKS +
                '}';
    }
}
