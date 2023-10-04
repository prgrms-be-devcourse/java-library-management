package org.example.server.exception;

public class BookNotFoundException extends ServerException {
    public BookNotFoundException() {
        super(System.lineSeparator() + "[System] 존재하지 않는 도서번호 입니다." + System.lineSeparator());
    }
}
