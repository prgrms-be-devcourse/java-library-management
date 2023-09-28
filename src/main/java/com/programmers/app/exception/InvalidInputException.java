package com.programmers.app.exception;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException() {
        super("[System] 잘못된 입력입니다.");
    }
}
