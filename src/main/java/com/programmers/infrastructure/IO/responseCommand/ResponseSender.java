package com.programmers.infrastructure.IO.responseCommand;

import com.programmers.mediator.dto.ConsoleResponse;

public interface ResponseSender {
    boolean supports(ConsoleResponse ConsoleResponse);
    void sendResponse(ConsoleResponse response);
}
