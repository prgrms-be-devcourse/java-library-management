package com.programmers.exception.unchecked;

import com.programmers.exception.ErrorCode;

public class BookNotFoundException extends UncheckedCustomException {

    public BookNotFoundException() {
        super(ErrorCode.NOT_FOUND_BOOK);
    }
}
