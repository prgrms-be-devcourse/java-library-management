package org.example.server;

import org.example.packet.responsePacket.BookResponseDto;
import org.example.packet.requestPacket.*;
import org.example.packet.responsePacket.ResponseSuccessWithData;
import org.example.packet.responsePacket.ResponseSuccessWithNoData;
import org.example.server.service.Service;

import java.util.LinkedList;

public class BookController {
    private final Service service;

    public BookController(Service service) {
        this.service = service;
    }

    public ResponseSuccessWithNoData register(RequestForRegister RequestForRegister) {
        service.register(RequestForRegister.bookInfo);
        return new ResponseSuccessWithNoData(RequestHandler.MethodType.REGISTER.name());
    }

    public ResponseSuccessWithData readAll(RequestForReadAll requestForReadAll) {
        LinkedList<BookResponseDto> books = service.readAll();
        return new ResponseSuccessWithData(RequestHandler.MethodType.READ_ALL.name(), books);
    }

    public ResponseSuccessWithData searchByName(RequestForSearch requestForSearch) {
        LinkedList<BookResponseDto> books = service.searchAllByName(requestForSearch.bookName);
        return new ResponseSuccessWithData(RequestHandler.MethodType.SEARCH_BY_NAME.name(), books);
    }

    public ResponseSuccessWithNoData borrow(RequestForChange requestForChange) {
        service.borrow(requestForChange.bookId);
        return new ResponseSuccessWithNoData(RequestHandler.MethodType.BORROW.name());
    }

    public ResponseSuccessWithNoData restore(RequestForChange requestForChange) {
        service.restore(requestForChange.bookId);
        return new ResponseSuccessWithNoData(RequestHandler.MethodType.RESTORE.name());
    }

    public ResponseSuccessWithNoData lost(RequestForChange requestForChange) {
        service.lost(requestForChange.bookId);
        return new ResponseSuccessWithNoData(RequestHandler.MethodType.LOST.name());
    }

    public ResponseSuccessWithNoData delete(RequestForChange requestForChange) {
        service.delete(requestForChange.bookId);
        return new ResponseSuccessWithNoData(RequestHandler.MethodType.DELETE.name());
    }
}
