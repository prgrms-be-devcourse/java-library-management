package com.programmers.common;

public enum ErrorMessages {
    INVALID_INPUT("[System] 잘못된 입력입니다."),
    BOOK_NOT_EXIST("[System] 존재하지 않는 도서입니다."),
    CSV_FORMAT_ERROR("[System] CSV 데이터의 형식이 도서 형식과 다릅니다."),
    BOOK_REGISTER_FAILED("[System] 도서 등록에 실패하였습니다."),
    BOOK_ALREADY_RENTED("[System] 이미 대여중인 도서입니다."),
    BOOK_BEING_ORGANIZED("[System] 현재 도서가 정리중입니다."),
    BOOK_NOW_LOST("[System] 이 도서는 분실되었습니다."),
    BOOK_ALREADY_AVAILABLE("[System] 원래 대여가 가능한 도서입니다."),
    BOOK_ALREADY_LOST("[System] 이미 분실 처리된 도서입니다."),
    FILE_NOT_FOUND("[System] 읽어올 데이터가 존재하지 않습니다. 빈 파일에 새로운 데이터가 저장됩니다.");

    private final String message;

    ErrorMessages(String message) {
        this.message = System.lineSeparator() + message + System.lineSeparator();
    }

    public String getMessage() {
        return this.message;
    }
}
