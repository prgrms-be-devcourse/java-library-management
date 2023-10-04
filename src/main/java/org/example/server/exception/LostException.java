package org.example.server.exception;

public class LostException extends ServerException {
    public LostException() {
        super(System.lineSeparator() + "[System] 이미 분실 처리된 도서입니다." + System.lineSeparator());
    }
}
