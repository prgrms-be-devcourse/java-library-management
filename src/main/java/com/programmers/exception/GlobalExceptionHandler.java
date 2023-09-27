package com.programmers.exception;

import com.programmers.exception.unchecked.UncheckedCustomException;
import com.programmers.presentation.UserInteraction;

import java.io.IOException;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
    private final UserInteraction userInteraction;

    public GlobalExceptionHandler(UserInteraction userInteraction) {
        this.userInteraction = userInteraction;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (e instanceof UncheckedCustomException) {
            handleUncheckedCustomException((UncheckedCustomException) e);
        } else if (e instanceof IOException) {
            handleIOException((IOException) e);
        } else {
            // 기타 예외 처리
            handleGeneralException(e);
        }
    }

    private void handleUncheckedCustomException(UncheckedCustomException e) {
        userInteraction.displayMessage(e.getErrorCode().getMessage());
    }

    private void handleIOException(IOException e) {
        userInteraction.displayMessage(e.getMessage());
    }

    private void handleGeneralException(Throwable e) {
        userInteraction.displayMessage(e.getMessage());
    }
}

