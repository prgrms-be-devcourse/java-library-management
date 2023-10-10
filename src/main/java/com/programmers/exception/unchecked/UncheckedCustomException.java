package com.programmers.exception.unchecked;

import com.programmers.exception.ErrorCode;
import lombok.Getter;

@Getter
public class UncheckedCustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public UncheckedCustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
