package com.programmers.exception.checked;

import com.programmers.exception.ErrorCode;

public class InvalidModeNumberException extends ValidationException {

    public InvalidModeNumberException() {
        super(ErrorCode.INVALID_MODE_NUMBER);
    }
}
