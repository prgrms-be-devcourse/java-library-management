package com.programmers.librarymanagement.exception;

public class SelectNotFoundException extends RuntimeException {

    public SelectNotFoundException() {
        super("존재하지 않는 선택지입니다.");
    }
}
