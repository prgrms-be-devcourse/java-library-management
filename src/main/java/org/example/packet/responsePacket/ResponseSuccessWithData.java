package org.example.packet.responsePacket;

import org.example.packet.BookDto;
import org.example.packet.MethodType;

import java.util.LinkedList;

public class ResponseSuccessWithData extends ResponsePacket {

    private LinkedList<BookDto> bookDtos;

    public ResponseSuccessWithData(MethodType method, LinkedList<BookDto> bookDtos) {
        super(method);
        this.bookDtos = bookDtos;
    }

    public LinkedList<BookDto> getBookDtos() {
        return bookDtos;
    }
}
