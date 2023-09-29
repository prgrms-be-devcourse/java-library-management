package constant;

public enum Guide {
    START_NORMAL_MODE("[System] 일반 모드로 애플리케이션을 실행합니다.\n"),
    START_TEST_MODE("[System] 테스트 모드로 애플리케이션을 실행합니다.\n"),
    REGISTER_START("[System] 도서 등록 메뉴로 넘어갑니다.\n"),
    REGISTER_END("[System] 도서 등록이 완료되었습니다.\n"),
    FIND_ALL_START("[System] 전체 도서 목록입니다.\n"),
    FIND_ALL_END("[System] 도서 목록 끝\n"),
    FIND_BY_TITLE_START("[System] 제목으로 도서 검색 메뉴로 넘어갑니다.\n"),
    FIND_BY_TITLE_END("[System] 검색된 도서 끝\n"),
    BORROW_START("[System] 도서 대여 메뉴로 넘어갑니다.\n"),
    BORROW_COMPLETE("[System] 도서가 대여 처리 되었습니다.\n"),
    RETURN_START("[System] 도서 반납 메뉴로 넘어갑니다.\n"),
    RETURN_COMPLETE("[System] 도서가 반납 처리 되었습니다.\n"),
    LOST_START("[System] 도서 분실 처리 메뉴로 넘어갑니다.\n"),
    LOST_COMPLETE("[System] 도서가 분실 처리 되었습니다.\n"),
    DELETE_START("[System] 도서 삭제 처리 메뉴로 넘어갑니다.\n"),
    DELETE_COMPLETE("[System] 도서가 삭제 처리 되었습니다.\n"),
    SYSTEM_END("[System] 시스템을 종료합니다."),
    WRONG_MODE("[System] 모드를 정확히 선택해주세요."),
    ;
    private final String guide;

    Guide(String guide) {
        this.guide = guide;
    }

    public String getGuide() {
        return guide;
    }
}