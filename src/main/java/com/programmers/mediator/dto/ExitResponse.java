package com.programmers.mediator.dto;

import com.programmers.util.Messages;
import lombok.Getter;

@Getter
public class ExitResponse extends ConsoleResponse<String> {
    private boolean isExit;

    public ExitResponse(String message, boolean isExit) {
        super(message);
        this.isExit = isExit;
    }

    public static ExitResponse noBodyResponse(boolean isExit) {
        if (isExit)
            return new ExitResponse(Messages.EXIT_MESSAGE.getMessage(), true);
        else
            return new ExitResponse(Messages.RETURN_MENU.getMessage(), false);
    }

}
