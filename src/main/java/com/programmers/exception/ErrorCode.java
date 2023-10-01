package com.programmers.exception;

public enum ErrorCode {

    NOT_NUMBER("[[System] 숫자로만 입력해주세요."),
    NUMBER_OVER_BOUNDARY("[System] 옵션은 지정된 범위의 숫자만 입력해주세요."),
    NUMBER_UNDER_ZERO("[System] 0 이상의 숫자로 입력해주세요. "),
    BOOK_NOT_FOUND("[System] 찾으시는 책이 존재하지 않습니다."),
    BOOK_CANNOT_RENTED("[System] 대여할 수 없는 도서입니다."),
    BOOK_CANNOT_RETURN("[System] 반납할 수 없는 도서입니다."),
    ALREADY_LOST("[System] 이미 분실된 도서입니다."),
    ALREADY_DELETED("[System] 이미 분실된 도서입니다.");
    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
