package com.programmers.exception.unchecked;

import com.programmers.exception.ErrorCode;

public class BookReturnFailedException extends UncheckedCustomException {

    public BookReturnFailedException() {
        super(ErrorCode.FAILED_RETURN_BOOK);
    }
}
