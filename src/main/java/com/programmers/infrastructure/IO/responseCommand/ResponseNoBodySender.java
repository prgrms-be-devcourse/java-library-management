package com.programmers.infrastructure.IO.responseCommand;

import com.programmers.infrastructure.IO.ConsoleInteractionAggregator;
import com.programmers.mediator.dto.ConsoleNoBodyResponse;
import com.programmers.mediator.dto.ConsoleResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResponseNoBodySender implements ResponseSender {

    private final ConsoleInteractionAggregator interactionAggregator;

    @Override
    public Class getProcessClass() {
        return ConsoleNoBodyResponse.class;
    }

    @Override
    public void sendResponse(ConsoleResponse response) {
        interactionAggregator.displayMessage(response.getMessage());
    }
}
