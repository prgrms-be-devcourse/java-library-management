package com.programmers.exception.unchecked;

import static com.programmers.exception.ErrorCode.FAILED_SET_AVAILABLE_BOOK;

public class BookAvailableFailedException extends UncheckedCustomException {

    public BookAvailableFailedException() {
        super(FAILED_SET_AVAILABLE_BOOK);
    }
}
