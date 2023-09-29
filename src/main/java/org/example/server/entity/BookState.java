package org.example.server.entity;

import org.example.server.exception.*;

import java.util.function.Supplier;

public enum BookState {
    CAN_BORROW("대여 가능", BookCanBorrowException::new),
    BORROWED("대여중", BookBorrowedException::new),
    LOADING("도서 정리중", BookLoadingException::new),
    LOST("분실됨", BookLostException::new);
    private final String status;
    private final Supplier<ServerException> statusExceptionManager;

    BookState(String status, Supplier<ServerException> statusExceptionManager) {
        this.status = status;
        this.statusExceptionManager = statusExceptionManager;
    }

    public String getStatus() {
        return status;
    }

    public ServerException throwStatusException() {
        return statusExceptionManager.get();
    }

}
