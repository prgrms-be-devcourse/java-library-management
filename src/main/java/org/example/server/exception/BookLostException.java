package org.example.server.exception;

public class BookLostException extends ServerException {
    @Override
    public String getMessage() {
        return "\n[System] 이미 분실 처리된 도서입니다.\n";
    }
}
