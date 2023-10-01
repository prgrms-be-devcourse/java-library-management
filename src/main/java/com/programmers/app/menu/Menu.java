package com.programmers.app.menu;

public enum Menu {
    EXIT(0),
    REGISTER(1),
    FIND_ALL_BOOKS(2),
    SEARCH_TITLE(3),
    BORROW_BOOK(4),
    RETURN_BOOK(5),
    REPORT_LOST(6),
    DELETE_BOOK(7);

    private final int menuCode;

    Menu(int menuCode) {
        this.menuCode = menuCode;
    }

    public boolean isSelected(int menuCode) {
        return this.menuCode == menuCode;
    }
}
