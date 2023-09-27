package com.programmers.exception.checked;

import com.programmers.exception.ErrorCode;

public class InvalidModeNumberException extends ValidationException {
    public InvalidModeNumberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
