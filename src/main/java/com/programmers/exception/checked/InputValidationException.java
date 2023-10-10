package com.programmers.exception.checked;


import com.programmers.exception.ErrorCode;

public class InputValidationException extends ValidationException {

    public InputValidationException() {
        super(ErrorCode.INVALID_INPUT);
    }
}

