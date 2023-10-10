package com.programmers.infrastructure.IO.responseCommand;

import com.programmers.infrastructure.IO.ConsoleInteractionAggregator;
import com.programmers.mediator.dto.ConsoleResponse;
import com.programmers.mediator.dto.ConsoleSingleBodyResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResponseSingleBodySender implements ResponseSender{

    private final ConsoleInteractionAggregator interactionAggregator;
    private final List<Class<? extends ConsoleResponse>> supportedResponses = List.of(
        ConsoleSingleBodyResponse.class);

    @Override
    public <T extends ConsoleResponse> boolean supports(Class<T> responseClass) {
        return supportedResponses.contains(responseClass);
    }

    @Override
    public void sendResponse(ConsoleResponse response) {
        response.getBody().ifPresent(interactionAggregator::displaySingleInfo);
        interactionAggregator.displayMessage(response.getMessage());
    }
}
