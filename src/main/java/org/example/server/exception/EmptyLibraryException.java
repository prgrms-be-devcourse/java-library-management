package org.example.server.exception;

public class EmptyLibraryException extends ServerException {
    @Override
    public String getMessage() {
        return System.lineSeparator() + "[System] 존재하는 도서가 없습니다." + System.lineSeparator();
    }
}
