package message;

public enum QuestionMessage {
    REGISTER_TITLE("Q. 등록할 도서 제목을 입력하세요."),
    REGISTER_WRITER("Q. 작가 이름을 입력하세요."),
    REGISTER_PAGE("Q. 페이지 수를 입력하세요."),
    SEARCH_TITLE("Q. 검색할 도서 제목 일부를 입력하세요."),
    RENTAL_ID("Q. 대여할 도서번호를 입력하세요"),
    RETURN_ID("Q. 반납할 도서번호를 입력하세요"),
    LOST_ID("Q. 분실 처리할 도서번호를 입력하세요"),
    DELETE_ID("Q. 삭제 처리할 도서번호를 입력하세요");

    private final String message;
    QuestionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
