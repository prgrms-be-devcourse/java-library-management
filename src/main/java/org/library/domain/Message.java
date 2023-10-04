package org.library.domain;

public enum Message {

    INPUT_USE_FUNCTION("Q. 사용할 기능을 선택해주세요."),
    INPUT_USE_MODE("Q. 모드를 선택해주세요."),

    START_REGISTER("[System] 도서 등록 메뉴로 넘어갑니다."),
    INPUT_REGISTER_TITLE("Q. 등록할 도서 제목을 입력하세요."),
    INPUT_REGISTER_AUTHOR("Q. 작가 이름을 입력하세요."),
    INPUT_REGISTER_PAGE("Q. 페이지 수를 입력하세요."),
    SUCCESS_REGISTER("[System] 도서 등록이 완료되었습니다."),

    START_FIND_ALL("[System] 전체 도서 목록입니다."),
    END_FIND_ALL("[System] 도서 목록 끝"),

    START_FIND_BY_TITLE("[System] 제목으로 도서 검색 메뉴로 넘어갑니다."),
    INPUT_FIND_TITLE("Q. 검색할 도서 제목 일부를 입력하세요."),
    END_FIND_BY_TITLE("[System] 검색된 도서 끝"),

    START_RENT("[System] 도서 대여 메뉴로 넘어갑니다."),
    INPUT_RENT_ID("Q. 대여할 도서 번호를 입력하세요."),
    SUCCESS_RENT("[System] 도서가 대여 처리 되었습니다."),

    START_RETURNS("[System] 도서 반납 메뉴로 넘어갑니다."),
    INPUT_RETURNS_ID("Q. 반납할 도서 번호를 입력하세요."),
    SUCCESS_RETURNS("[System] 도서가 반납 처리 되었습니다."),

    START_REPORT_LOST("[System] 도서 분실 처리 메뉴로 넘어갑니다."),
    INPUT_REPORT_LOST_ID("Q. 분실 처리할 도서 번호를 입력하세요."),
    SUCCESS_REPORT_LOST("[System] 도서가 분실 처리 되었습니다."),

    START_DELETE("[System] 도서 삭제 처리 메뉴로 넘어갑니다."),
    INPUT_DELETE_ID("Q. 삭제 처리할 도서 번호를 입력하세요."),
    SUCCESS_DELETE("[System] 도서가 삭제 처리 되었습니다."),

    QUIT("[System] 도서 관리 프로그램을 종료합니다.");


    private String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
