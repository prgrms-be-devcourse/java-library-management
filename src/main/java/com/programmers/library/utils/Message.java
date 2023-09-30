package com.programmers.library.utils;

public enum Message {
    ADD_SUCCESS("도서 등록이 완료되었습니다."),         // SUCCESS
    RENT_SUCCESS("도서가 대여 처리 되었습니다."),
    RETURN_SUCCESS("도서가 반납 처리 되었습니다."),
    LOST_SUCCESS("도서가 분실 처리 되었습니다."),
    DELETE_SUCCESS("도서가 삭제 처리 되었습니다."),

    LIST_START("전체 도서 목록입니다."),             // PRINT
    LIST_END("도서 목록 끝"),
    SEARCH_END("검색된 도서 끝"),

    NO_BOOKS("현재 등록된 도서가 없습니다."),           // ERROR
    NO_SEARCH_BOOKS("검색된 도서가 없습니다."),
    BOOK_NOT_FOUND("존재하지 않는 도서번호 입니다."),
    ALREADY_RENTED("이미 대여중인 도서입니다."),
    NOT_AVAILABLE("해당 도서는 대여가 불가능합니다."),
    CANNOT_RETURN("해당 도서는 반납할 수 없습니다."),
    ALREADY_LOST("이미 분실 처리된 도서입니다.");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
