package org.library.entity;

public enum Error {

    INVALID_INPUT_MODE("[System] 모드 선택이 잘못되었습니다."),
    INVALID_INPUT_FUNC("[System] 기능 선택이 잘못되었습니다."),
    ALREADY_RENT("이미 대여중인 도서입니다."),
    ALREADY_REPORT_LOST("이미 분실 처리된 도서입니다."),
    ALREADY_ORGANIZING("이미 정리중인 도서입니다."),
    NOT_EXIST("존재하지 않는 도서입니다."),
    NOT_AVAILABLE("대여할 수 없는 상태입니다."),
    NOT_RETURNS("반납할 수 없는 상태입니다.");

    private final String message;

    Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
