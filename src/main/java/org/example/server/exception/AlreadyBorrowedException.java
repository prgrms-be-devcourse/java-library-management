package org.example.server.exception;

public class AlreadyBorrowedException extends ServerException {
    public AlreadyBorrowedException() {
        super(System.lineSeparator() + "[System] 이미 대여중인 도서입니다." + System.lineSeparator());
    }
}
