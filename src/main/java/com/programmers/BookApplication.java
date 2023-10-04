package com.programmers;

import com.programmers.config.DependencyInjector;
import com.programmers.exception.AppExceptionHandler;
import com.programmers.mediator.RequestProcessor;
import com.programmers.mediator.dto.Request;
import com.programmers.mediator.dto.Response;

public class BookApplication implements Runnable {

    private final RequestProcessor requestProcessor;
    private final AppExceptionHandler exceptionHandler;

    public BookApplication(RequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
        this.exceptionHandler = DependencyInjector.getInstance().getAppExceptionHandler();
    }

    @Override
    public void run() {
        // TODO: 성능 상 익명구현객체 제거하고 선언하는 게 좋음
        while (true) {
            exceptionHandler.handle(() -> {
                Request request = requestProcessor.getRequest();
                Response response = requestProcessor.processRequest(request);
                requestProcessor.sendResponse(response);
            });
        }
    }
}
