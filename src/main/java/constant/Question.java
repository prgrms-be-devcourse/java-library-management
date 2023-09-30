package constant;

public enum Question {
    REGISTER_TITLE("Q. 등록할 도서 제목을 입력하세요."),
    REGISTER_AUTHOR("Q. 작가 이름을 입력하세요."),
    REGISTER_PAGE_NUM("Q. 페이지 수를 입력하세요."),
    FIND_BY_TITLE("Q. 검색할 도서 제목 일부를 입력하세요."),
    BORROW_BY_BOOK_NO("Q. 대여할 도서번호를 입력하세요"),
    RETURN_BY_BOOK_NO("Q. 반납할 도서번호를 입력하세요"),
    LOST_BY_BOOK_NO("Q. 분실 처리할 도서번호를 입력하세요"),
    DELETE_BY_BOOK_NO("Q. 삭제 처리할 도서번호를 입력하세요"),
    ;

    private final String question;

    Question(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }
}
