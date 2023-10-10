package com.libraryManagement.exception;

public enum ExceptionMessage {
    INVALID_BOOK_MENU("[System] 메뉴 숫자를 다시 입력해주세요.\n[System] 종료하려면 0을 입력해주세요.\n"),
    INVALID_MODE_MENU("[System] 모드 숫자를 다시 입력해주세요."),

    BOOK_NOT_FOUND_ID("[System] 존재하지 않는 도서번호 입니다."),
    BOOK_ALREADY_AVAILABLE("[System] 원래 대여가 가능한 도서입니다."),
    BOOK_ALREADY_RENTED("[System] 이미 대여중인 도서입니다."),
    BOOK_ALREADY_LOST("[System] 이미 분실 처리된 도서입니다."),
    BOOK_STILL_ORGANIZING("[System] 아직 정리중인 도서입니다.\n" +
                            "[System] 5분 내로 대여가능합니다."),

    FILE_NOT_EXIST("[System] 파일이 존재하지 않습니다."),
    FILE_WRITE_FAILED("[System] 파일 쓰기에 실패했습니다.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
