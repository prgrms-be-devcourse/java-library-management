package com.programmers.infrastructure.IO;

import com.programmers.domain.enums.MenuCommand;
import com.programmers.mediator.RequestProcessor;
import com.programmers.mediator.dto.Request;
import com.programmers.mediator.dto.Response;
import com.programmers.presentation.Controller;
import com.programmers.presentation.UserInteraction;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConsoleRequestProcessor implements RequestProcessor<String,String> {
    private final Controller controller;
    private final UserInteraction userInteraction;

    public Request<String> getRequest() {
        userInteraction.displayMessage("메뉴 선택 메세지");
        return userInteraction::collectUserInput;
    }

    public void sendResponse(Response<String> response) {
        userInteraction.displayMessage("성공");
    }

    @Override
    public Response<String> processRequest(Request<String> request) {
        return MenuCommand.executeServiceByOptionNum(request.getRequest(), controller);
    }
}
