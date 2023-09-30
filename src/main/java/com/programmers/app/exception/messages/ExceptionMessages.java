package com.programmers.app.exception.messages;

public enum ExceptionMessages {

    //book not found
    NO_BOOK_LOADED("[System] 보관된 책이 없습니다."),
    TITLE_NONEXISTENT("[System] 존재하지 않는 제목입니다."),
    BOOK_NUMBER_NONEXISTENT("[System] 존재하지 않는 도서번호 입니다."),

    //action not allowed
    ALREADY_BORROWED("[System] 이미 대여중인 도서입니다."),
    BOOK_LOSS_REPORTED("[System] 분실 처리된 도서입니다."),
    BOOK_ON_ARRANGEMENT("[System] 정리중인 도서입니다."),
    ALREADY_RETURNED("[System] 이미 반납 처리된 도서입니다."),
    ALREADY_REPORTED_LOST("[System] 이미 분실 처리된 도서입니다."),

    //invalid input
    INCORRECT_INPUT_NUMBER("[System] 잘못된 번호입니다. 다시 입력해주세요.");

    private final String exceptionMessage;

    ExceptionMessages(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
