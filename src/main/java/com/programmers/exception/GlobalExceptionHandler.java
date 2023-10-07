package com.programmers.exception;

import com.programmers.mediator.RequestProcessor;
import com.programmers.mediator.dto.ConsoleNoBodyResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {

    private final RequestProcessor requestProcessor;

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        handleGeneralException(e);
    }

    private void handleGeneralException(Throwable e) {
        requestProcessor.sendResponse(ConsoleNoBodyResponse.noBodyResponse(e.getMessage()));
        System.exit(1);
    }
}

