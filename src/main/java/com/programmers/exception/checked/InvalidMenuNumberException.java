package com.programmers.exception.checked;

import com.programmers.exception.ErrorCode;

public class InvalidMenuNumberException extends ValidationException{
    public InvalidMenuNumberException() {
        super(ErrorCode.INVALID_MENU_NUMBER);
    }
}
