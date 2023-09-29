package org.example.server.exception;

public class BookCanBorrowException extends ServerException {
    @Override
    public String getMessage() {
        return "\n[System] 원래 대여가 가능한 도서입니다.\n";
    }
}
