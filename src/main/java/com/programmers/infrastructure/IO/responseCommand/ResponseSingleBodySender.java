package com.programmers.infrastructure.IO.responseCommand;

import com.programmers.infrastructure.IO.ConsoleInteractionAggregator;
import com.programmers.mediator.dto.ConsoleResponse;
import com.programmers.mediator.dto.ConsoleSingleBodyResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResponseSingleBodySender implements ResponseSender{

    private final ConsoleInteractionAggregator interactionAggregator;

    @Override
    public Class<? extends ConsoleResponse> getProcessClass() {
        return ConsoleSingleBodyResponse.class;
    }

    @Override
    public void sendResponse(ConsoleResponse response) {
        response.getBody().ifPresent(interactionAggregator::displaySingleInfo);
        interactionAggregator.displayMessage(response.getMessage());
    }
}
