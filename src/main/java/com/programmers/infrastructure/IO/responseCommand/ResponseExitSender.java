package com.programmers.infrastructure.IO.responseCommand;

import com.programmers.infrastructure.IO.ConsoleInteractionAggregator;
import com.programmers.mediator.dto.ConsoleResponse;
import com.programmers.mediator.dto.ExitResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResponseExitSender implements ResponseSender {

    private final ConsoleInteractionAggregator interactionAggregator;
    private final List<Class<? extends ConsoleResponse>> supportedResponses = List.of(
        ExitResponse.class);

    @Override
    public boolean supports(ConsoleResponse consoleResponse) {
        return supportedResponses.contains(consoleResponse.getClass());
    }

    @Override
    public void sendResponse(ConsoleResponse response) {
        interactionAggregator.displayMessage(response.getMessage());
        if (((ExitResponse) response).isExit()) {
            System.exit(0);
        }
    }
}
