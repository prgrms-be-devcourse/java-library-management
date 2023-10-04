package org.example.server.controller;

import org.example.packet.BookDto;
import org.example.packet.MethodType;
import org.example.packet.requestPacket.*;
import org.example.packet.responsePacket.ResponseFailWithMessage;
import org.example.packet.responsePacket.ResponsePacket;
import org.example.packet.responsePacket.ResponseSuccessWithData;
import org.example.packet.responsePacket.ResponseSuccessWithNoData;
import org.example.server.exception.ServerException;
import org.example.server.service.Service;

import java.util.LinkedList;

public class BookController implements Controller {
    private final Service service;

    public BookController(Service service) {
        this.service = service;
    }

    public ResponsePacket handleRequest(RequestPacket requestPacket) {
        MethodType method = requestPacket.getMethod();
        try {
            return switch (method) {
                case REGISTER -> register((RequestWithBook) requestPacket);
                case READ_ALL -> readAll((RequestWithNoData) requestPacket);
                case SEARCH_BY_NAME -> searchByName((RequestWithName) requestPacket);
                case BORROW -> borrow((RequestWithId) requestPacket);
                case RESTORE -> restore((RequestWithId) requestPacket);
                case LOST -> lost((RequestWithId) requestPacket);
                case DELETE -> delete((RequestWithId) requestPacket);
            };
        } catch (ServerException e) {
            return new ResponseFailWithMessage(method, e.getMessage());
        }
    }

    private ResponseSuccessWithNoData register(RequestWithBook requestWithBook) {
        service.register(requestWithBook.getBookDto());
        return new ResponseSuccessWithNoData(MethodType.REGISTER);
    }

    private ResponseSuccessWithData readAll(RequestWithNoData requestWithNoData) {
        LinkedList<BookDto> books = service.readAll();
        return new ResponseSuccessWithData(MethodType.READ_ALL, books);
    }

    private ResponseSuccessWithData searchByName(RequestWithName requestWithName) {
        LinkedList<BookDto> books = service.searchByName(requestWithName.getName());
        return new ResponseSuccessWithData(MethodType.SEARCH_BY_NAME, books);
    }

    private ResponseSuccessWithNoData borrow(RequestWithId requestWithId) {
        service.borrow(requestWithId.getId());
        return new ResponseSuccessWithNoData(MethodType.BORROW);
    }

    private ResponseSuccessWithNoData restore(RequestWithId requestWithId) {
        service.restore(requestWithId.getId());
        return new ResponseSuccessWithNoData(MethodType.RESTORE);
    }

    private ResponseSuccessWithNoData lost(RequestWithId requestWithId) {
        service.lost(requestWithId.getId());
        return new ResponseSuccessWithNoData(MethodType.LOST);
    }

    private ResponseSuccessWithNoData delete(RequestWithId requestWithId) {
        service.delete(requestWithId.getId());
        return new ResponseSuccessWithNoData(MethodType.DELETE);
    }
}
