package com.dev_course.library;

public enum LibraryMessage {
    MOD_SCREEN_MESSAGE("""
            Q. 모드를 선택해주세요.
            0. 테스트 모드
            1. 일반 모드
            """),
    FUNCTION_SCREEN_MESSAGE("""
            Q. 사용할 기능을 선택해주세요.
            0. 프로그램 종료
            1. 도서 등록
            2. 전체 도서 목록 조회
            3. 제목으로 도서 검색
            4. 도서 대여
            5. 도서 반납
            6. 도서 분실
            7. 도서 삭제
            """),
    EXIT_MESSAGE("[System] 프로그램을 종료합니다."),
    INVALID_MODE_MESSAGE("[System] 사용할 수 없는 모드 입니다.\n"),
    INVALID_FUNCTION_MESSAGE("[System] 사용할 수 없는 기능 입니다.\n"),
    EMPTY_INPUT_MESSAGE("[System] 입력을 읽을 수 없습니다.\n"),
    INVALID_INPUT_MESSAGE("[System] 잘못된 입력 입니다.\n"),
    CREATE_BOOK_MESSAGE("[System] 도서 등록 메뉴로 넘어갑니다.\n"),
    LIST_BOOK_MESSAGE("[System] 전체 도서 목록입니다.\n"),
    END_LIST_BOOK_MESSAGE("[System] 도서 목록 끝\n"),
    FIND_BOOK_BY_TITLE_MESSAGE("[System] 제목으로 도서 검색 메뉴로 넘어갑니다.\n"),
    END_FIND_BOOK_BY_TITLE_MESSAGE("[System] 검색된 도서 끝\n"),
    RENT_BOOK_BY_ID_MESSAGE("[System] 도서 대여 메뉴로 넘어갑니다.\n"),
    RETURN_BOOK_BY_ID_MESSAGE("[System] 도서 반납 메뉴로 넘어갑니다.\n"),
    LOSS_BOOK_BY_ID_MESSAGE("[System] 도서 분실 처리 메뉴로 넘어갑니다.\n"),
    DELETE_BOOK_MESSAGE("[System] 도서 삭제 처리 메뉴로 넘어갑니다.\n"),
    READ_CREATE_BOOK_TITLE_MESSAGE("Q. 등록할 도서 제목을 입력하세요."),
    READ_CREATE_BOOK_AUTHOR_MESSAGE("Q. 작가 이름을 입력하세요."),
    READ_CREATE_BOOK_PAGES_MESSAGE("Q. 페이지 수를 입력하세요."),
    READ_FIND_BY_TITLE_MESSAGE("Q. 검색할 도서 제목 일부를 입력하세요."),
    READ_RENT_BOOK_BY_ID_MESSAGE("Q. 대여할 도서번호를 입력하세요."),
    READ_RETURN_BOOK_BY_ID_MESSAGE("Q. 반납할 도서번호를 입력하세요."),
    READ_LOSS_BOOK_BY_ID_MESSAGE("Q. 분실 처리할 도서번호를 입력하세요."),
    READ_DELETE_BOOK_ID_MESSAGE("Q. 삭제 처리할 도서번호를 입력하세요");


    private final String msg;

    LibraryMessage(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
