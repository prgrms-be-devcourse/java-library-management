package com.programmers.infrastructure.IO.responseCommand;

import com.programmers.infrastructure.IO.ConsoleInteractionAggregator;
import com.programmers.mediator.dto.ConsoleResponse;
import com.programmers.mediator.dto.ExitResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResponseExitSender implements ResponseSender{

    private final ConsoleInteractionAggregator interactionAggregator;

    @Override
    public Class<? extends ConsoleResponse> getProcessClass() {
        return ExitResponse.class;
    }

    @Override
    public void sendResponse(ConsoleResponse response) {
        interactionAggregator.displayMessage(response.getMessage());
        if (((ExitResponse) response).isExit()) {
            System.exit(0);
        }
    }
}
