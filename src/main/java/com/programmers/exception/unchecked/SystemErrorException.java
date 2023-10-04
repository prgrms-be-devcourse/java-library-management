package com.programmers.exception.unchecked;

import com.programmers.exception.ErrorCode;

public class SystemErrorException extends UncheckedCustomException {

    public SystemErrorException() {
        super(ErrorCode.SYSTEM_ERROR);
    }
}
