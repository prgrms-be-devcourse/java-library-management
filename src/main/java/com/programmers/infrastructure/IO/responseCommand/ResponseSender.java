package com.programmers.infrastructure.IO.responseCommand;

import com.programmers.mediator.dto.ConsoleResponse;

public interface ResponseSender {
    Class<? extends ConsoleResponse> getProcessClass();
    void sendResponse(ConsoleResponse response);
}
