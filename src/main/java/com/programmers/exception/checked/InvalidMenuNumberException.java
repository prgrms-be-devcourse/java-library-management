package com.programmers.exception.checked;

import com.programmers.exception.ErrorCode;

public class InvalidMenuNumberException extends ValidationException{
    public InvalidMenuNumberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
