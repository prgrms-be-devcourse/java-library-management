package com.programmers.exception;

import com.programmers.exception.unchecked.UncheckedCustomException;
import com.programmers.mediator.RequestProcessor;
import com.programmers.mediator.dto.ConsoleResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AppExceptionHandler {

    private final RequestProcessor requestProcessor;

    public void handle(Runnable runnable) {
        try {
            runnable.run();
        } catch (UncheckedCustomException e) {
            userInteraction.displayMessage(e.getErrorCode().getMessage());
        } catch (Exception e) {
            requestProcessor.sendResponse(
                ConsoleResponse.noBodyResponse(e.getMessage()));
        }
    }
}
