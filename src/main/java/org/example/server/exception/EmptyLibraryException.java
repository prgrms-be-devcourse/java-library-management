package org.example.server.exception;

public class EmptyLibraryException extends ServerException {
    @Override
    public String getMessage() {
        return "\n[System] 존재하는 도서가 없습니다.\n";
    }
}
