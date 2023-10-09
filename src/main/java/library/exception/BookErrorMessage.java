package library.exception;

public enum BookErrorMessage {

    BOOK_ALREADY_AVAILABLE("원래 대여가 가능한 도서입니다."),
    BOOK_ALREADY_EXISTS("이미 존재하는 도서번호 입니다."),
    BOOK_ALREADY_RENTED("이미 대여중인 도서입니다."),
    BOOK_RENTED("도서가 대여 중입니다."),
    BOOK_NOT_FOUND("도서를 찾을 수 없습니다."),
    BOOK_IN_CLEANUP("도서가 정리 중입니다."),
    BOOK_LOST("도서가 분실 상태입니다."),
    BOOK_ALREADY_LOST("이미 분실 처리된 도서입니다.");

    private final String message;

    BookErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
