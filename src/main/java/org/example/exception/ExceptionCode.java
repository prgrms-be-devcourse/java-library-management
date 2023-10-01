package org.example.exception;

import org.example.domain.BookStatusType;

public enum ExceptionCode {
    AVAILABLE_FOR_BORROW("[System] 원래 대여가 가능한 도서입니다."),
    ALREADY_BORROWED("[System] 이미 대여 중인 도서입니다."),
    BEING_ORGANIZED("[System] 정리 중인 도서입니다."),
    LOST("[System] 분실 처리된 도서입니다."),
    INVALID_BOOK("[System] 존재하지 않는 도서번호 입니다.");

    private String message;

    ExceptionCode(String message) {
        this.message = message;
    }

    public static ExceptionCode getException(BookStatusType bookStatus) {
        switch (bookStatus) {
            case BORROW_AVAILABE:
                return AVAILABLE_FOR_BORROW;
            case BORROWING:
                return ALREADY_BORROWED;
            case ORGANIZING:
                return BEING_ORGANIZED;
            case LOST:
                return LOST;
            default:
                return INVALID_BOOK;
        }
    }

    public String getMessage() {
        return this.message;
    }
}
