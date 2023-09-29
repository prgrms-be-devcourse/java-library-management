package org.example.server.exception;

public class BookLoadingException extends ServerException {
    @Override
    public String getMessage() {
        return "\n[System] 해당 도서는 정리중입니다.\n";
    }
}
