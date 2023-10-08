package com.dev_course.library;

public enum LibraryMessage {
    MOD_SCREEN("""
            Q. 모드를 선택해주세요.
            0. 테스트 모드
            1. 일반 모드"""),
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
    INVALID_MODE("[System] 사용할 수 없는 모드 입니다."),
    INVALID_FUNCTION("[System] 사용할 수 없는 기능 입니다."),
    EMPTY_INPUT("[System] 입력을 읽을 수 없습니다."),
    NOT_EXIST_ID("[System] 존재하지 않는 도서번호입니다."),
    INVALID_INPUT("[System] 잘못된 입력 입니다."),
    CREATE_BOOK("[System] 도서 등록 메뉴로 넘어갑니다."),
    LIST_BOOK("[System] 전체 도서 목록입니다."),
    END_LIST_BOOK("[System] 도서 목록 끝"),
    FIND_BOOK_BY_TITLE("[System] 제목으로 도서 검색 메뉴로 넘어갑니다."),
    END_FIND_BOOK_BY_TITLE("[System] 검색된 도서 끝"),
    RENT_BOOK_BY_ID("[System] 도서 대여 메뉴로 넘어갑니다."),
    RETURN_BOOK_BY_ID("[System] 도서 반납 메뉴로 넘어갑니다."),
    LOSS_BOOK_BY_ID("[System] 도서 분실 처리 메뉴로 넘어갑니다."),
    DELETE_BOOK("[System] 도서 삭제 처리 메뉴로 넘어갑니다."),
    ALREADY_EXIST_TITLE("[System] 이미 존재하는 도서입니다."),
    SUCCESS_CREATE_BOOK("[System] 도서 등록이 완료되었습니다."),
    SUCCESS_RENT_BOOK("[System] 도서가 대여 처리되었습니다."),
    FAIL_RENT_BOOK("[System] 대여할 수 없는 도서입니다."),
    SUCCESS_RETURN_BOOK("[System] 도서가 반납 처리되었습니다."),
    FAIL_RETURN_BOOK("[System] 이미 반납된 도서 입니다."),
    SUCCESS_LOSS_BOOK("[System] 도서가 분실 처리되었습니다."),
    ALREADY_LOST_BOOK("[System] 이미 분실 처리된 도서입니다."),
    SUCCESS_DELETE_BOOK("[System] 도서가 삭제 처리되었습니다."),
    READ_CREATE_BOOK_TITLE("Q. 등록할 도서 제목을 입력하세요."),
    READ_CREATE_BOOK_AUTHOR("Q. 작가 이름을 입력하세요."),
    READ_CREATE_BOOK_PAGES("Q. 페이지 수를 입력하세요."),
    READ_FIND_BY_TITLE("Q. 검색할 도서 제목 일부를 입력하세요."),
    READ_RENT_BOOK_BY_ID("Q. 대여할 도서번호를 입력하세요."),
    READ_RETURN_BOOK_BY_ID("Q. 반납할 도서번호를 입력하세요."),
    READ_LOSS_BOOK_BY_ID("Q. 분실 처리할 도서번호를 입력하세요."),
    READ_DELETE_BOOK_ID("Q. 삭제 처리할 도서번호를 입력하세요");

    private final String msg;

    LibraryMessage(String msg) {
        this.msg = msg;
    }

    public String msg() {
        return msg;
    }
}
