package com.dev_course.book;

public enum BookManagerMessage {
    ALREADY_EXIST_TITLE("[System] 이미 존재하는 도서입니다."),
    NOT_EXIST_ID("[System] 존재하지 않는 도서번호입니다."),
    SUCCESS_CREATE_BOOK("[System] 도서 등록이 완료되었습니다."),
    SUCCESS_RENT_BOOK("[System] 도서가 대여 처리되었습니다."),
    FAIL_RENT_BOOK("[System] 대여할 수 없는 도서입니다."),
    SUCCESS_RETURN_BOOK("[System] 도서가 반납 처리되었습니다."),
    FAIL_RETURN_BOOK("[System] 이미 반납된 도서 입니다."),
    SUCCESS_LOSS_BOOK("[System] 도서가 분실 처리되었습니다."),
    ALREADY_LOST_BOOK("[System] 이미 분실 처리된 도서입니다."),
    SUCCESS_DELETE_BOOK("[System] 도서가 삭제 처리되었습니다.");

    private final String msg;

    BookManagerMessage(String msg) {
        this.msg = msg;
    }

    public String msg() {
        return msg;
    }
}
