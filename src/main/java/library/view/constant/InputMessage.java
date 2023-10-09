package library.view.constant;

public enum InputMessage {

    MODE("모드를 선택해주세요."),
    MENU("사용할 기능을 선택해주세요."),
    TITLE("등록할 도서 제목을 입력하세요."),
    AUTHOR("작가 이름을 입력하세요."),
    PAGE_COUNT("페이지 수를 입력하세요."),
    SEARCH_BY_TITLE("검색할 도서 제목 일부를 입력하세요."),
    RENT_BOOK("대여할 도서번호를 입력하세요."),
    RETURN_BOOK("반납할 도서번호를 입력하세요."),
    LOST_BOOK("분실 처리할 도서번호를 입력하세요."),
    DELETE_BOOK("삭제할 도서번호를 입력하세요.");

    private final String message;

    InputMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
