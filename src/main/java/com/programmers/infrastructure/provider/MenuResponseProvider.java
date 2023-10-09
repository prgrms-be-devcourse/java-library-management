package com.programmers.infrastructure.provider;

import com.programmers.infrastructure.IO.responseCommand.ResponseSender;
import com.programmers.mediator.dto.ConsoleResponse;
import java.util.List;

public class MenuResponseProvider {

    private final List<ResponseSender> responseSenders;

    public MenuResponseProvider(List<ResponseSender> responseSenders) {
        this.responseSenders = responseSenders;
    }

    public void sendMenuResponse(ConsoleResponse<?> response) {
        responseSenders.stream()
                .filter(sender -> sender.supports(response.getClass()))
                .findFirst()
                .ifPresent(sender -> sender.sendResponse(response));
    }
}
