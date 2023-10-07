package org.example.server;

import org.example.packet.BookResponseDto;
import org.example.packet.requestPacket.RequestWithBook;
import org.example.packet.requestPacket.RequestWithId;
import org.example.packet.requestPacket.RequestWithName;
import org.example.packet.requestPacket.RequestWithNoData;
import org.example.packet.responsePacket.ResponseSuccessWithData;
import org.example.packet.responsePacket.ResponseSuccessWithNoData;
import org.example.server.service.Service;

import java.util.LinkedList;

public class BookController {
    private final Service service;

    public BookController(Service service) {
        this.service = service;
    } // 각각 행위에 맞는 네이밍

    public ResponseSuccessWithNoData register(RequestWithBook requestWithBook) {
        service.register(requestWithBook.bookInfo);
        return new ResponseSuccessWithNoData(RequestHandler.MethodType.REGISTER.name());
    }

    public ResponseSuccessWithData readAll(RequestWithNoData requestWithNoData) {
        LinkedList<BookResponseDto> books = service.readAll();
        return new ResponseSuccessWithData(RequestHandler.MethodType.READ_ALL.name(), books);
    }

    public ResponseSuccessWithData searchByName(RequestWithName requestWithName) {
        LinkedList<BookResponseDto> books = service.searchAllByName(requestWithName.bookName);
        return new ResponseSuccessWithData(RequestHandler.MethodType.SEARCH_BY_NAME.name(), books);
    }

    public ResponseSuccessWithNoData borrow(RequestWithId requestWithId) {
        service.borrow(requestWithId.bookId);
        return new ResponseSuccessWithNoData(RequestHandler.MethodType.BORROW.name());
    }

    public ResponseSuccessWithNoData restore(RequestWithId requestWithId) {
        service.restore(requestWithId.bookId);
        return new ResponseSuccessWithNoData(RequestHandler.MethodType.RESTORE.name());
    }

    public ResponseSuccessWithNoData lost(RequestWithId requestWithId) {
        service.lost(requestWithId.bookId);
        return new ResponseSuccessWithNoData(RequestHandler.MethodType.LOST.name());
    }

    public ResponseSuccessWithNoData delete(RequestWithId requestWithId) {
        service.delete(requestWithId.bookId);
        return new ResponseSuccessWithNoData(RequestHandler.MethodType.DELETE.name());
    }
}
