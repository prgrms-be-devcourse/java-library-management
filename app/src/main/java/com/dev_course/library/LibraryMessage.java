package com.dev_course.library;

public enum LibraryMessage {
    MOD_SCREEN("""
            Q. 모드를 선택해주세요.
            1. 일반 모드
            2. 테스트 모드
            """),
    FUNCTION_SCREEN("""
            Q. 사용할 기능을 선택해주세요.
            0. 프로그램 종료
            1. 도서 등록
            2. 전체 도서 목록 조회
            3. 제목으로 도서 검색
            4. 도서 대여
            5. 도서 반납
            6. 도서 분실
            7. 도서 삭제"""),
    EXIT("[System] 프로그램을 종료합니다."),
    INVALID_NUMBER("[System] 사용할 수 없는 기능입니다."),
    CREATE_BOOK("[System] 도서 등록 메뉴로 넘어갑니다."),
    LIST_BOOK("[System] 전체 도서 목록입니다."),
    FIND_BOOK_BY_TITLE("[System] 제목으로 도서 검색 메뉴로 넘어갑니다.");

    private final String msg;

    LibraryMessage(String msg) {
        this.msg = msg;
    }

    public String msg() {
        return msg;
    }
}
