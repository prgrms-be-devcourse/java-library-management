package com.programmers.exception.checked;

import com.programmers.exception.ErrorCode;

public class InvalidExitCommandException extends ValidationException{
    public InvalidExitCommandException(ErrorCode errorCode) {
        super(errorCode);
    }
}
