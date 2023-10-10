package com.programmers.exception.unchecked;


import com.programmers.exception.ErrorCode;

public class BookLostFailedException extends UncheckedCustomException {

    public BookLostFailedException() {
        super(ErrorCode.FAILED_LOST_BOOK);
    }
}
