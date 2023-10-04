package org.example.server.entity;

import org.example.server.exception.ServerException;

public enum BookState {
    CAN_BORROW("대여 가능", new ServerException() {
        @Override
        public String getMessage() {
            return System.lineSeparator() + "[System] 원래 대여가 가능한 도서입니다." + System.lineSeparator();
        }
    }), BORROWED("대여중", new ServerException() {
        @Override
        public String getMessage() {
            return System.lineSeparator() + "[System] 이미 대여중인 도서입니다." + System.lineSeparator();
        }
    }), LOADING("도서 정리중", new ServerException() {
        @Override
        public String getMessage() {
            return System.lineSeparator() + "[System] 해당 도서는 이미 반납되어, 도서 정리중입니다." + System.lineSeparator();
        }
    }), LOST("분실됨", new ServerException() {
        @Override
        public String getMessage() {
            return System.lineSeparator() + "[System] 이미 분실 처리된 도서입니다." + System.lineSeparator();
        }
    });
    private final String status;
    private final ServerException statusException;

    BookState(String status, ServerException statusException) {
        this.status = status;
        this.statusException = statusException;
    }

    public String getStatus() {
        return status;
    }

    public ServerException throwStatusException() {
        return statusException;
    }
}
