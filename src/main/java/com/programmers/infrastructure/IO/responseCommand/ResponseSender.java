package com.programmers.infrastructure.IO.responseCommand;

import com.programmers.mediator.dto.ConsoleResponse;

public interface ResponseSender {
    <T extends ConsoleResponse> boolean supports(Class<T> responseClass);
    void sendResponse(ConsoleResponse response);
}
