package constant;

public enum ExceptionMsg {
    ALREADY_BORROWED("[System] 이미 대여중인 도서입니다."),
    IS_LOST("[System] 분실된 도서입니다."),
    IS_ORGANIZING("[System] 정리중인 도서입니다."),
    ALREADY_LOST("[System] 이미 분실 처리된 도서입니다."),
    RETURN_FAIL("[System] 반납에 실패했습니다. 원래 대여가 가능한 도서입니다."),
    NO_TARGET("[System] 존재하지 않는 도서번호 입니다."),
    WRONG_MODE("[System] 모드를 정확히 선택해주세요."),
    WRONG_SELECTION("[System] 존재하지 않는 선택지 입니다.");

    private final String message;

    ExceptionMsg(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
