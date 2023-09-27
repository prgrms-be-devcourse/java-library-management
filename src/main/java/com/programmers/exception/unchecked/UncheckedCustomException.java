package com.programmers.exception.unchecked;

import com.programmers.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UncheckedCustomException extends RuntimeException {
    private final ErrorCode errorCode;
}
