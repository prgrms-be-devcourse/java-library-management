package com.programmers.app.menu;

public enum Menu {
    EXIT(0, "종료"),
    REGISTER(1, "등록"),
    FIND_ALL_BOOKS(2, "전체 조회"),
    SEARCH_TITLE(3, "제목 조회"),
    BORROW_BOOK(4, "대여"),
    RETURN_BOOK(5, "반납"),
    REPORT_LOST(6, "분실"),
    DELETE_BOOK(7, "삭제");

    private final int menuCode;
    private final String menuName;

    Menu(int menuCode, String menuName) {
        this.menuCode = menuCode;
        this.menuName = menuName;
    }

    public boolean isSelected(int menuCode) {
        return this.menuCode == menuCode;
    }

    public String displayName() {
        return this.menuName;
    }
}
