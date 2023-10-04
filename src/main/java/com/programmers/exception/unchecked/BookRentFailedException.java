package com.programmers.exception.unchecked;

import com.programmers.exception.ErrorCode;

public class BookRentFailedException extends UncheckedCustomException {

    public BookRentFailedException() {
        super(ErrorCode.FAILED_RENT_BOOK);
    }
}
