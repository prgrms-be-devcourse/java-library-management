package org.example.server.exception;

public class AlreadyCanBorrowException extends ServerException {
    public AlreadyCanBorrowException() {
        super(System.lineSeparator() + "[System] 원래 대여가 가능한 도서입니다." + System.lineSeparator());
    }
}