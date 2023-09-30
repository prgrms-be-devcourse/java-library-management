package com.programmers.common;

public enum InfoMessages {

    // 모드 선택 결과 메세지
    NORMAL_MODE_EXECUTION("[System] 일반 모드로 애플리케이션을 실행합니다."),
    TEST_MODE_EXECUTION("[System] 테스트 모드로 애플리케이션을 실행합니다."),

    // 도서 등록 기능
    MOVE_TO_BOOK_REGISTER("[System] 도서 등록 메뉴로 넘어갑니다."),
    BOOK_REGISTER_SUCCESS("[System] 도서 등록이 완료되었습니다."),


    // 도서 조회 기능
    BOOK_LIST_START("[System] 전체 도서 목록입니다."),
    BOOK_LIST_FINISH("[System] 도서 목록 끝"),

    // 도서 검색 기능
    MOVE_TO_BOOK_SEARCH("[System] 제목으로 도서 검색 메뉴로 넘어갑니다."),
    BOOK_SEARCH_FINISH("[System] 검색된 도서 끝"),

    // 도서 대여 기능
    MOVE_TO_BOOK_RENT("[System] 도서 대여 메뉴로 넘어갑니다."),
    BOOK_RENT_SUCCESS("[System] 도서가 대여 처리 되었습니다."),


    // 도서 반납 기능
    MOVE_TO_BOOK_RETURN("[System] 도서 반납 메뉴로 넘어갑니다."),
    BOOK_RETURN_SUCCESS("[System] 도서가 반납 처리 되었습니다."),


    // 도서 분실 기능
    MOVE_TO_BOOK_LOST("[System] 도서 분실 메뉴로 넘어갑니다."),
    BOOK_LOST_SUCCESS("[System] 도서가 분실 처리 되었습니다."),


    // 도서 삭제 기능
    MOVE_TO_BOOK_DELETE("[System] 도서 삭제 처리 메뉴로 넘어갑니다."),
    BOOK_DELETE_SUCCESS("[System] 도서가 삭제 처리 되었습니다.");

    private final String message;

    InfoMessages(String message) {
        this.message = System.lineSeparator() + message + System.lineSeparator();
    }

    public String getMessage() {
        return this.message;
    }
}
