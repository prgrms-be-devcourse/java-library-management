package org.library.exception;

public enum Exception {

    INVALID_INPUT_MODE("[System] 모드 선택이 잘못되었습니다."),
    INVALID_INPUT_FUNC("[System] 기능 선택이 잘못되었습니다."),
    ALREADY_RENT("이미 대여중인 도서입니다."),
    ALREADY_REPORT_LOST("이미 분실 처리된 도서입니다."),
    ALREADY_ORGANIZING("이미 정리중인 도서입니다."),
    NOT_EXIST("존재하지 않는 도서입니다."),
    NOT_AVAILABLE("대여할 수 없는 상태입니다."),
    NOT_RETURNS("반납할 수 없는 상태입니다."),

    INVALID_TITLE("빈 제목을 입력할 수 없습니다."),
    INVALID_AUTHOR("빈 작가 이름을 입력할 수 없습니다."),
    INVALID_PAGE("페이지는 음수일 수 없습니다.");

    private final String message;

    Exception(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
