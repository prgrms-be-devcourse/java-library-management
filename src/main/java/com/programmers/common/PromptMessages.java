package com.programmers.common;

public enum PromptMessages {
    // 모드 선택
    MODE_CHOICE_MESSAGE("""     
            Q. 모드를 선택해주세요.
            1. 일반 모드
            2. 테스트 모드
            """),

    // 내부 기능 선택
    BOOK_MANAGEMENT_FEATURE_MESSAGE("""
            Q. 사용할 기능을 선택해주세요.
            1. 도서 등록
            2. 전체 도서 목록 조회
            3. 제목으로 도서 검색
            4. 도서 대여
            5. 도서 반납
            6. 도서 분실
            7. 도서 삭제
            """),
    //PROMPT MESSAGE
    BOOK_TITLE_PROMPT("Q. 검색할 도서 제목 일부를 입력하세요."),
    BOOK_RENT_PROMPT("Q. 대여할 도서번호를 입력하세요."),
    BOOK_RETURN_PROMPT("Q. 반납할 도서번호를 입력하세요."),
    BOOK_LOST_PROMPT("Q. 분실 처리할 도서번호를 입력하세요."),
    BOOK_DELETE_PROMPT("Q. 삭제 처리할 도서번호를 입력하세요.");
    private final String message;

    PromptMessages(String message) {
        String NEWLINE = System.lineSeparator();
        this.message = NEWLINE + message + NEWLINE + "> ";
    }

    public String getMessage() {
        return this.message;
    }
}
