package org.example.server;

import org.example.packet.requestPacket.*;
import org.example.packet.responsePacket.ResponseFailWithMessage;
import org.example.packet.responsePacket.ResponsePacket;
import org.example.server.exception.ServerException;

public class RequestHandler {
    private final BookController bookController;

    RequestHandler(BookController bookController) {
        this.bookController = bookController;
    }

    public ResponsePacket handleRequest(RequestPacket requestPacket) {
        MethodType methodType = MethodType.valueOf(requestPacket.METHOD);
        try {
            return switch (methodType) {
                case REGISTER -> bookController.register((RequestWithBook) requestPacket);
                case READ_ALL -> bookController.readAll((RequestWithNoData) requestPacket);
                case SEARCH_BY_NAME -> bookController.searchByName((RequestWithName) requestPacket);
                case BORROW -> bookController.borrow((RequestWithId) requestPacket);
                case RESTORE -> bookController.restore((RequestWithId) requestPacket);
                case LOST -> bookController.lost((RequestWithId) requestPacket);
                case DELETE -> bookController.delete((RequestWithId) requestPacket);
            };
        } catch (ServerException e) {
            return new ResponseFailWithMessage(methodType.name(), e.getMessage());
        }
    }

    public enum MethodType {
        REGISTER,
        READ_ALL,
        SEARCH_BY_NAME,
        BORROW,
        RESTORE,
        LOST,
        DELETE;
    }
}
