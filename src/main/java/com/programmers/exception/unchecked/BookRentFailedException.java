package com.programmers.exception.unchecked;

import com.programmers.exception.ErrorCode;

public class BookRentFailedException extends UncheckedCustomException {

    public BookRentFailedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
