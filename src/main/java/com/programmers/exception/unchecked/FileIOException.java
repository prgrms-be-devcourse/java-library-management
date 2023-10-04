package com.programmers.exception.unchecked;

import com.programmers.exception.ErrorCode;

public class FileIOException extends UncheckedCustomException {

    public FileIOException() {
        super(ErrorCode.FILE_IO_ERROR);
    }
}
