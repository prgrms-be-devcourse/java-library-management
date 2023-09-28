package com.programmers.library.config;

import com.programmers.library.exception.ErrorCode;
import com.programmers.library.exception.ExceptionHandler;
import com.programmers.library.file.FileHandler;
import com.programmers.library.file.FileUtil;
import com.programmers.library.repository.FileRepository;
import com.programmers.library.repository.TestRepository;
import com.programmers.library.service.LibraryService;

public class LibraryServiceFactory {

    private static final int NORMAL_MODE = 1;
    private static final int TEST_MODE = 2;

    public LibraryService createLibraryService(int mode) {
        switch (mode) {
            case NORMAL_MODE:
                return new LibraryService(new FileRepository(new FileHandler(new FileUtil())));
            case TEST_MODE:
                return new LibraryService(new TestRepository());
            default:
                throw ExceptionHandler.err(ErrorCode.INVALID_MODE_EXCEPTION);
        }
    }
}
