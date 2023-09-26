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
                        
            >\s""");
    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}