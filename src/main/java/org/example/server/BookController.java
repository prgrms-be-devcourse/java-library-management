package org.example.server;

import org.example.packet.BookDto;
import org.example.packet.requestPacket.RequestWithBook;
import org.example.packet.requestPacket.RequestWithId;
import org.example.packet.requestPacket.RequestWithName;
import org.example.packet.requestPacket.RequestWithNoData;
import org.example.packet.responsePacket.ResponseSuccessWithData;
import org.example.packet.responsePacket.ResponseSuccessWithNoData;
import org.example.server.service.Service;

import java.util.LinkedList;

public class BookController {
    private final Service SERVICE;

    public BookController(Service service) {
        this.SERVICE = service;
    } // 각각 행위에 맞는 네이밍

    public ResponseSuccessWithNoData register(RequestWithBook requestWithBook) {
        SERVICE.register(requestWithBook.BOOK_INFO);
        return new ResponseSuccessWithNoData(RequestHandler.MethodType.REGISTER.name());
    }

    public ResponseSuccessWithData readAll(RequestWithNoData requestWithNoData) {
        LinkedList<BookDto> books = SERVICE.readAll();
        return new ResponseSuccessWithData(RequestHandler.MethodType.READ_ALL.name(), books);
    }

    public ResponseSuccessWithData searchByName(RequestWithName requestWithName) {
        LinkedList<BookDto> books = SERVICE.searchByName(requestWithName.NAME);
        return new ResponseSuccessWithData(RequestHandler.MethodType.SEARCH_BY_NAME.name(), books);
    }

    public ResponseSuccessWithNoData borrow(RequestWithId requestWithId) {
        SERVICE.borrow(requestWithId.ID);
        return new ResponseSuccessWithNoData(RequestHandler.MethodType.BORROW.name());
    }

    public ResponseSuccessWithNoData restore(RequestWithId requestWithId) {
        SERVICE.restore(requestWithId.ID);
        return new ResponseSuccessWithNoData(RequestHandler.MethodType.RESTORE.name());
    }

    public ResponseSuccessWithNoData lost(RequestWithId requestWithId) {
        SERVICE.lost(requestWithId.ID);
        return new ResponseSuccessWithNoData(RequestHandler.MethodType.LOST.name());
    }

    public ResponseSuccessWithNoData delete(RequestWithId requestWithId) {
        SERVICE.delete(requestWithId.ID);
        return new ResponseSuccessWithNoData(RequestHandler.MethodType.DELETE.name());
    }
}
