package com.programmers;

import com.programmers.config.DependencyInjector;
import com.programmers.config.enums.ExitCommand;
import com.programmers.mediator.RequestProcessor;
import com.programmers.mediator.dto.Request;
import com.programmers.mediator.dto.Response;

public class BookApplication implements Runnable{
    private final RequestProcessor requestProcessor;

    public BookApplication(RequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }

    @Override
    public void run() {
        while (true) {
            Request request = requestProcessor.getRequest();
            Response response = requestProcessor.processRequest(request);
            requestProcessor.sendResponse(response);

            ExitCommand.promptForExit(DependencyInjector.getInstance().getUserInteraction());
        }
    }
}
