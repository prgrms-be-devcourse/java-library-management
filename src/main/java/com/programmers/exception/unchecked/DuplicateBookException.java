package com.programmers.exception.unchecked;

import com.programmers.exception.ErrorCode;

public class DuplicateBookException extends UncheckedCustomException {

    public DuplicateBookException() {
        super(ErrorCode.DUPLICATE_BOOK);
    }
}

