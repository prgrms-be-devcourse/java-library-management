package org.example.server;

import org.example.packet.requestPacket.*;
import org.example.packet.responsePacket.ResponseFail;
import org.example.packet.responsePacket.ResponsePacket;
import org.example.server.exception.ServerException;

public class RequestHandler { // 인터페이스로 만들고 각각에 맞는 구현체-> (고민해보기) ex. RequestWithNoData에 맞도록 매핑
    private final BookController bookController;

    RequestHandler(BookController bookController) {
        this.bookController = bookController;
    }

    public ResponsePacket handleRequest(RequestPacket requestPacket) { // 인터페이스를 타입 캐스팅 -> 
        MethodType methodType = MethodType.valueOf(requestPacket.methodName);
        try {
            return switch (methodType) {
                case REGISTER -> bookController.register((RequestForRegister) requestPacket);
                case READ_ALL -> bookController.readAll((RequestForReadAll) requestPacket);
                case SEARCH_BY_NAME -> bookController.searchByName((RequestForSearch) requestPacket);
                case BORROW -> bookController.borrow((RequestForChange) requestPacket);
                case RESTORE -> bookController.restore((RequestForChange) requestPacket);
                case LOST -> bookController.lost((RequestForChange) requestPacket);
                case DELETE -> bookController.delete((RequestForChange) requestPacket);
            };
        } catch (ServerException e) {
            return new ResponseFail(methodType.name(), e.getMessage());
        }
    }

    public enum MethodType {
        REGISTER,
        READ_ALL,
        SEARCH_BY_NAME,
        BORROW,
        RESTORE,
        LOST,
        DELETE
    }
}
