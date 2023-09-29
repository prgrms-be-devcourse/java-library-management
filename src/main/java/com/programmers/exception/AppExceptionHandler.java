package com.programmers.exception;

import com.programmers.exception.unchecked.UncheckedCustomException;
import com.programmers.presentation.UserInteraction;


public class AppExceptionHandler {
    private final UserInteraction userInteraction;

    public AppExceptionHandler(UserInteraction userInteraction) {
        this.userInteraction = userInteraction;
    }

    public void handle(Runnable runnable) {
        try {
            runnable.run();
        } catch (UncheckedCustomException e) {
            userInteraction.displayMessage(e.getErrorCode().getMessage());
        } catch (Exception e) {
            userInteraction.displayMessage(e.getMessage());
        }
    }
}
