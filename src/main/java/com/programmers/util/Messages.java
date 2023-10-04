package com.programmers.util;

public enum Messages {
    SELECT_MODE("select.mode"),
    SELECT_MODE_NORMAL("select.mode.normal"),
    SELECT_MODE_TEST("select.mode.test"),
    EXIT_PROMPT("exitConfirmation.prompt"),
    SELECT_MENU("select.menu"),
    SELECT_MENU_REGISTER("select.menu.register"),
    SELECT_MENU_SEARCH("select.menu.search"),
    SELECT_MENU_DELETE("select.menu.delete"),
    SELECT_MENU_RENT("select.menu.rent"),
    SELECT_MENU_RETURN("select.menu.return"),
    SELECT_MENU_LOST("select.menu.lost"),
    SELECT_MENU_LIST("select.menu.list"),
    SELECT_MENU_EXIT("select.menu.exit"),
    RETURN_MENU("return.mainMenu"),
    BOOK_SEARCH_TITLE("book.search.title"),
    BOOK_DELETE_ID("book.delete.id"),
    BOOK_RENT_ID("book.rent.id"),
    BOOK_RETURN_ID("book.return.id"),
    BOOK_LOST_ID("book.lost.id"),
    BOOK_REGISTER_TITLE("book.register.title"),
    BOOK_REGISTER_AUTHOR("book.register.author"),
    BOOK_REGISTER_PAGES("book.register.pages"),
    BOOK_REGISTER_SUCCESS("book.register.success"),
    BOOK_LIST_SUCCESS("book.list.success"),
    BOOK_SEARCH_SUCCESS("book.search.success"),
    BOOK_DELETE_SUCCESS("book.delete.success"),
    BOOK_RENT_SUCCESS("book.rent.success"),
    BOOK_RETURN_SUCCESS("book.return.success"),
    BOOK_LOST_SUCCESS("book.lost.success"),

    ;

    private final String key;

    Messages(String key) {
        this.key = key;
    }

    public String getMessage() {
        return MessageProperties.get(key);
    }
}
