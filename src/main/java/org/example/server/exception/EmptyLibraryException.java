package org.example.server.exception;

public class EmptyLibraryException extends ServerException {
    public EmptyLibraryException() {
        super(System.lineSeparator() + "[System] 존재하는 도서가 없습니다." + System.lineSeparator());
    }
}
