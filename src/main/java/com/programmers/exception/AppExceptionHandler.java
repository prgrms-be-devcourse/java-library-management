package com.programmers.exception;

import com.programmers.exception.unchecked.SystemErrorException;
import com.programmers.exception.unchecked.UncheckedCustomException;
import com.programmers.mediator.RequestProcessor;
import com.programmers.mediator.dto.ConsoleNoBodyResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AppExceptionHandler {

    private final RequestProcessor requestProcessor;

    public void handle(Runnable runnable) {
        try {
            runnable.run();
        } catch (SystemErrorException e) {
            requestProcessor.sendResponse(
                ConsoleNoBodyResponse.noBodyResponse(e.getErrorCode().getMessage()));
            System.exit(1);
        } catch (UncheckedCustomException e) {
            requestProcessor.sendResponse(
                ConsoleNoBodyResponse.noBodyResponse(e.getErrorCode().getMessage()));
        } catch (Exception e) {
            requestProcessor.sendResponse(
                ConsoleNoBodyResponse.noBodyResponse(e.getMessage()));
        }
    }
}
