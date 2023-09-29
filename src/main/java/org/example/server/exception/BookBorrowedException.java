package org.example.server.exception;

public class BookBorrowedException extends ServerException {
    @Override
    public String getMessage() {
        return "\n[System] 이미 대여중인 도서입니다.\n";
    }
}
