package org.example.app;

public enum LibraryFunctionType {
    REGISTER_BOOK(1, "Register Book"),
    SEARCH_ALL_BOOKS(2, "Search All Books"),
    SEARCH_BOOKS_BY_TITLE(3, "Search Books by Title"),
    BORROW_BOOK(4, "Borrow Book"),
    RETURN_BOOK(5, "Return Book"),
    LOST_BOOK(6, "Lost Book"),
    DELETE_BOOK(7, "Delete Book");

    private Integer number;
    private String name;

    LibraryFunctionType(Integer number, String name) {
        this.number = number;
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public static LibraryFunctionType getValueByNumber(Integer number) {
        for (LibraryFunctionType libraryFunctionType : LibraryFunctionType.values()) {
            if (libraryFunctionType.getNumber().equals(number)) {
                return libraryFunctionType;
            }
        }

        throw new IllegalArgumentException("1부터 7까지의 정수 중 하나를 입력해주세요.");
    }
}
