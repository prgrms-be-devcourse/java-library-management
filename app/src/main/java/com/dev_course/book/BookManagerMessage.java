package com.dev_course.book;

public enum BookManagerMessage {
    ALREADY_EXIST_TITLE("[System] 이미 존재하는 도서입니다.\n"),
    NOT_EXIST_ID("[System] 존재하지 않는 도서번호입니다.\n"),
    SUCCESS_CREATE_BOOK("[System] 도서 등록이 완료되었습니다.\n"),
    SUCCESS_RENT_BOOK("[System] 도서가 대여 처리되었습니다.\n"),
    FAIL_RENT_BOOK("[System] 대여할 수 없는 도서입니다."),
    SUCCESS_DELETE_BOOK("[System] 도서가 삭제 처리되었습니다.\n");

    private final String msg;

    BookManagerMessage(String msg) {
        this.msg = msg;
    }

    public String msg() {
        return msg;
    }
}
