package com.programmers.exception;

import com.programmers.util.MessageProperties;

public enum ErrorCode {
    INVALID_MODE_NUMBER("invalid.mode"),
    INVALID_INPUT("invalid.input"),
    INVALID_EXIT("invalid.exit"),
    INVALID_MENU_NUMBER("invalid.menu"),
    DUPLICATE_BOOK("duplicate.book"),
    NOT_FOUND_BOOK("not.found.book"),
    FAILED_RENT_BOOK_RENTED("failed.rent.book.rented"),
    FAILED_RENT_BOOK_ORGANIZING("failed.rent.book.organizing"),
    FAILED_RENT_BOOK_LOST("failed.rent.book.lost"),
    FAILED_RETURN_BOOK("failed.return.book"),
    FAILED_LOST_BOOK("failed.lost.book"),
    SYSTEM_ERROR("system.error"),
    FILE_IO_ERROR("file.io.error"),
    ;

    ErrorCode(String key) {
        this.key = key;
    }

    private final String key;

    public String getMessage() {
        return MessageProperties.getError(key);
    }

}
