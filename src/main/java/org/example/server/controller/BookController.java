package org.example.server.controller;

import org.example.packet.BookDto;
import org.example.packet.requestPacket.*;
import org.example.packet.responsePacket.ResponseFailWithMessage;
import org.example.packet.responsePacket.ResponsePacket;
import org.example.packet.responsePacket.ResponseSuccessWithData;
import org.example.packet.responsePacket.ResponseSuccessWithNoData;
import org.example.server.exception.ServerException;
import org.example.server.service.Service;

import java.util.LinkedList;

public class BookController implements Controller {
    private final Service SERVICE;

    public BookController(Service service) {
        this.SERVICE = service;
    }

    public ResponsePacket handleRequest(RequestPacket requestPacket) {
        MethodType methodType = MethodType.valueOf(requestPacket.METHOD);
        try {
            return switch (methodType) {
                case REGISTER -> register((RequestWithBook) requestPacket);
                case READ_ALL -> readAll((RequestWithNoData) requestPacket);
                case SEARCH_BY_NAME -> searchByName((RequestWithName) requestPacket);
                case BORROW -> borrow((RequestWithId) requestPacket);
                case RESTORE -> restore((RequestWithId) requestPacket);
                case LOST -> lost((RequestWithId) requestPacket);
                case DELETE -> delete((RequestWithId) requestPacket);
            };
        } catch (ServerException e) {
            return new ResponseFailWithMessage(methodType.name(), e.getMessage());
        }
    }

    private ResponseSuccessWithNoData register(RequestWithBook requestWithBook) {
        SERVICE.register(requestWithBook.BOOK_INFO);
        return new ResponseSuccessWithNoData(MethodType.REGISTER.name());
    }

    private ResponseSuccessWithData readAll(RequestWithNoData requestWithNoData) {
        LinkedList<BookDto> books = SERVICE.readAll();
        return new ResponseSuccessWithData(MethodType.READ_ALL.name(), books);
    }

    private ResponseSuccessWithData searchByName(RequestWithName requestWithName) {
        LinkedList<BookDto> books = SERVICE.searchByName(requestWithName.NAME);
        return new ResponseSuccessWithData(MethodType.SEARCH_BY_NAME.name(), books);
    }

    private ResponseSuccessWithNoData borrow(RequestWithId requestWithId) {
        SERVICE.borrow(requestWithId.ID);
        return new ResponseSuccessWithNoData(MethodType.BORROW.name());
    }

    private ResponseSuccessWithNoData restore(RequestWithId requestWithId) {
        SERVICE.restore(requestWithId.ID);
        return new ResponseSuccessWithNoData(MethodType.RESTORE.name());
    }

    private ResponseSuccessWithNoData lost(RequestWithId requestWithId) {
        SERVICE.lost(requestWithId.ID);
        return new ResponseSuccessWithNoData(MethodType.LOST.name());
    }

    private ResponseSuccessWithNoData delete(RequestWithId requestWithId) {
        SERVICE.delete(requestWithId.ID);
        return new ResponseSuccessWithNoData(MethodType.DELETE.name());
    }

    private enum MethodType {
        REGISTER,
        READ_ALL,
        SEARCH_BY_NAME,
        BORROW,
        RESTORE,
        LOST,
        DELETE;
    }

}
