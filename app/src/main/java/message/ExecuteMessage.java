package message;

public enum ExecuteMessage {
    NORMAL_MODE("[System] 일반 모드로 애플리케이션을 실행합니다."),
    TEST_MODE("[System] 테스트 모드로 애플리케이션을 실행합니다."),
    REGISTER("[System] 도서 등록 메뉴로 넘어갑니다."),
    LIST("[System] 전체 도서 목록입니다."),
    SEARCH("[System] 제목으로 도서 검색 메뉴로 넘어갑니다."),
    RENTAL("[System] 도서 대여 메뉴로 넘어갑니다."),
    RETURN("[System] 도서 반납 메뉴로 넘어갑니다."),
    LOST("[System] 도서 분실 처리 메뉴로 넘어갑니다."),
    DELETE("[System] 도서 삭제 처리 메뉴로 넘어갑니다."),
    FINISH("프로그램을 종료합니다."),
    COMPLETE_REGISTER("[System] 도서 등록이 완료되었습니다."),
    LIST_FINISH("[System] 도서 목록 끝"),
    SEARCH_FINISH("[System] 검색된 도서 끝"),
    NOT_EXIST("[System] 존재하지 않는 도서 번호입니다."),
    RENTAL_RENTING("[System] 이미 대여중인 도서입니다."),
    RENTAL_AVAILABLE("[System] 도서가 대여 처리 되었습니다."),
    RENTAL_ORGANIZING("[System] 정리 중인 도서입니다."),
    RENTAL_LOST("[System] 분실된 도서입니다."),
    RETURN_COMPLETE("[System] 도서가 반납 처리 되었습니다."),
    RETURN_AVAILABLE("[System] 원래 대여가 가능한 도서입니다."),
    RETURN_IMPOSSIBLE("[System] 반납이 불가능한 도서입니다."),
    LOST_COMPLETE("[System] 도서가 분실 처리 되었습니다."),
    LOST_IMPOSSIBLE("[System] 분실 처리가 불가능한 도서입니다."),
    LOST_ALREADY("[System] 이미 분실 처리된 도서입니다."),
    DELETE_COMPLETE("[System] 도서가 삭제 처리 되었습니다.");

    ExecuteMessage(String message) {
    }
}
