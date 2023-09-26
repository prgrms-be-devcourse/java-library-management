package com.programmers;

import com.programmers.config.enums.ExitCommand;
import com.programmers.infrastructure.IO.Console;
import com.programmers.mediator.RequestProcessor;
import com.programmers.mediator.dto.Request;
import com.programmers.mediator.dto.Response;

public class BookApplicationRunner implements Runnable {
    private final Console console = Console.getInstance();
    private Boolean continueRunning = true;
    private final RequestProcessor requestProcessor;

    public BookApplicationRunner(RequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }

    private void checkContinueRunning() {
        console.printToConsole("""
                계속 진행 하시겠습니까? (y/n)""");
        String exitInput = console.getInput();
        continueRunning = !ExitCommand.isExit(exitInput);
    }
    @Override
    public void run() {
        // Http 면 http 서버 키는 로직 들어감
        while (continueRunning) {
            Request request = requestProcessor.getRequest();
            Response response = requestProcessor.processRequest(request);
            requestProcessor.sendResponse(response);
            checkContinueRunning();
        }
    }


}
