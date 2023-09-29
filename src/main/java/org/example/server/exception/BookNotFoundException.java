package org.example.server.exception;

public class BookNotFoundException extends ServerException {
    @Override
    public String getMessage() {
        return "[System] 존재하지 않는 도서번호 입니다.\n";
    }
}
