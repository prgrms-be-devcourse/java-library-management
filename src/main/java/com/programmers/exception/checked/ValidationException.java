package com.programmers.exception.checked;

import com.programmers.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ValidationException extends Exception {
    private final ErrorCode errorCode;

    public ValidationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
