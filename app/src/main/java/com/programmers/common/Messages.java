package com.programmers.common;

public enum Messages {
    MODE_CHOICE_MESSAGE("""
            Q. 모드를 선택해주세요.
            1. 일반 모드
            2. 테스트 모드
                        
            >\s"""),

    BOOK_MANAGEMENT_FEATURE_MESSAGE("""
            Q. 사용할 기능을 선택해주세요.
            1. 도서 등록
            2. 전체 도서 목록 조회
            3. 제목으로 도서 검색
            4. 도서 대여
            5. 도서 반납
            6. 도서 분실
            7. 도서 삭제
                        
            >\s"""),

    // 기능 선택 메세지
    NORMAL_MODE_EXECUTION("\n[System] 일반 모드로 애플리케이션을 실행합니다."),
    TEST_MODE_EXECUTION("\n[System] 테스트 모드로 애플리케이션을 실행합니다."),

    // ERROR MESSAGE
    INVALID_INPUT("\n[System] 잘못된 입력입니다."),
    BOOK_NOT_EXIST("\n[System] 존재하지 않는 도서입니다."),

    //PROMPT MESSAGE
    BOOK_TITLE_PROMPT("\nQ. 검색할 도서 제목 일부를 입력하세요.\n\n> "),
    BOOK_RENT_PROMPT("\nQ. 대여할 도서번호를 입력하세요\n\n> "),
    BOOK_RETURN_PROMPT("\nQ. 반납할 도서번호를 입력하세요\n\n> "),
    BOOK_LOST_PROMPT("\nQ. 분실 처리할 도서번호를 입력하세요\n\n> "),
    BOOK_DELETE_PROMPT("\nQ. 삭제 처리할 도서번호를 입력하세요\n\n> "),

    // 도서 등록 기능
    MOVE_TO_BOOK_REGISTER("\n[System] 도서 등록 메뉴로 넘어갑니다."),
    BOOK_REGISTER_SUCCESS("\n[System] 도서 등록이 완료되었습니다."),
    BOOK_REGISTER_FAILED("\n[System] 도서 등록에 실패하였습니다."),

    // 도서 조회 기능
    BOOK_LIST_START("\n[System] 전체 도서 목록입니다."),
    BOOK_LIST_FINISH("\n[System] 도서 목록 끝"),

    // 도서 검색 기능
    MOVE_TO_BOOK_SEARCH("\n[System] 제목으로 도서 검색 메뉴로 넘어갑니다."),
    BOOK_SEARCH_FINISH("\n[System] 검색된 도서 끝"),

    // 도서 대여 기능
    MOVE_TO_BOOK_RENT("\n[System] 도서 대여 메뉴로 넘어갑니다."),

    BOOK_RENT_SUCCESS("\n[System] 도서가 대여 처리 되었습니다."),
    BOOK_ALREADY_RENTED("\n[System] 이미 대여중인 도서입니다."),
    BOOK_BEING_ORGANIZED("\n[System] 현재 도서가 정리중입니다."),
    BOOK_NOW_LOST("\n[System] 이 도서는 분실되었습니다."),

    // 도서 반납 기능
    MOVE_TO_BOOK_RETURN("\n[System] 도서 반납 메뉴로 넘어갑니다.\n"),
    BOOK_RETURN_SUCCESS("\n[System] 도서가 반납 처리 되었습니다.\n"),
    BOOK_RETURN_FAILED("\n[System] 원래 대여가 가능한 도서입니다.\n"),

    // 도서 분실 기능
    MOVE_TO_BOOK_LOST("\n[System] 도서 분실 메뉴로 넘어갑니다.\n"),
    BOOK_LOST_SUCCESS("\n[System] 도서가 분실 처리 되었습니다.\n"),
    BOOK_LOST_FAILED("\n[System] 이미 분실 처리된 도서입니다.\n\n"),

    // 도서 삭제 기능
    MOVE_TO_BOOK_DELETE("\n[System] 도서 삭제 처리 메뉴로 넘어갑니다.\n"),
    BOOK_DELETE_SUCCESS("\n[System] 도서가 삭제 처리 되었습니다.\n");

    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}