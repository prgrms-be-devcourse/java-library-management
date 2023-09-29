package com.programmers.mediator;

import com.programmers.domain.enums.MenuCommand;
import com.programmers.exception.checked.InvalidMenuNumberException;
import com.programmers.mediator.dto.ConsoleRequest;
import com.programmers.mediator.dto.Request;
import com.programmers.mediator.dto.Response;
import com.programmers.presentation.Controller;
import com.programmers.presentation.UserInteraction;
import com.programmers.util.Messages;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConsoleRequestProcessor implements RequestProcessor<String,String> {
    private final Controller controller;
    private final UserInteraction userInteraction;

    @Override
    public Request<String> getRequest() {
        userInteraction.displayMessage(Messages.SELECT_MENU.getMessage());
        return ConsoleRequest.of(userInteraction.collectUserInput());
    }

    @Override
    public void sendResponse(Response<String> response) {
        userInteraction.displayMessage(response.getResponse());
    }

    @Override
    public Response<String> processRequest(Request<String> request) {
        try {
            return MenuCommand.routeToControllerByOptionNum(request.getRequest(), controller);
        } catch (InvalidMenuNumberException e) {
            userInteraction.displayMessage(e.getMessage());
            return processRequest(getRequest());
        }
    }

}
