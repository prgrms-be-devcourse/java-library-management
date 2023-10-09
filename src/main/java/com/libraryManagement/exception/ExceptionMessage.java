package com.libraryManagement.exception;

public enum ExceptionMessage {
    INVALID_BOOK_MENU("메뉴 선택이 잘못되었습니다"),
    INVALID_MODE_MENU("모드 선택이 잘못되었습니다."),

    BOOK_NOT_FOUND("[System] 존재하지 않는 도서번호 입니다."),
    BOOK_ALREADY_RENTED("[System] 이미 대여중인 도서입니다."),
    BOOK_LOST("[System] 이미 분실 처리된 도서입니다."),
    BOOK_UNDER_ORGANIZING("[System] 아직 정리중인 도서입니다."),
    BOOK_ALREADY_AVAILABLE("[System] 원래 대여가 가능한 도서입니다."),

    FILE_NOT_EXIST("파일이 존재하지 않습니다."),
    FILE_READ_FAILED("파일 읽기에 실패했습니다."),
    FILE_WRITE_FAILED("파일 쓰기에 실패했습니다.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
