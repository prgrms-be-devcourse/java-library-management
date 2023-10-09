package com.programmers.infrastructure.IO.responseCommand;

import com.programmers.infrastructure.IO.ConsoleInteractionAggregator;
import com.programmers.mediator.dto.ConsoleListBodyResponse;
import com.programmers.mediator.dto.ConsoleResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResponseListBodySender implements ResponseSender {

    private final ConsoleInteractionAggregator interactionAggregator;
    private final List<Class<? extends ConsoleResponse>> supportedResponses = List.of(
        ConsoleListBodyResponse.class);

    @Override
    public boolean supports(ConsoleResponse consoleResponse) {
        return supportedResponses.contains(consoleResponse.getClass());
    }

    @Override
    public void sendResponse(ConsoleResponse response) {
        response.getBody().ifPresent(body -> interactionAggregator.displayListInfo((List) body));
        interactionAggregator.displayMessage(response.getMessage());
    }
}
