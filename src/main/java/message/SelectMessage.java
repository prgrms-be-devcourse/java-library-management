package message;

public enum SelectMessage {
    MODE_SELECT_MESSAGE("""
            Q. 모드를 선택해주세요.
            1. 일반 모드
            2. 테스트 모드
            """),
    FUNCTION_SELECT_MESSAGE("Q. 사용할 기능을 선택해주세요.\n" +
            "1. 도서 등록\n" +
            "2. 전체 도서 목록 조회\n" +
            "3. 제목으로 도서 검색\n" +
            "4. 도서 대여\n" +
            "5. 도서 반납\n" +
            "6. 도서 분실\n" +
            "7. 도서 삭제");


    private String message;
    SelectMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
