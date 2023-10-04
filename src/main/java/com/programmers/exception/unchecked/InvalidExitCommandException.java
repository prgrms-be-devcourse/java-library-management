package com.programmers.exception.unchecked;

import com.programmers.exception.ErrorCode;

public class InvalidExitCommandException extends UncheckedCustomException {

    public InvalidExitCommandException() {
        super(ErrorCode.INVALID_EXIT);
    }
}
