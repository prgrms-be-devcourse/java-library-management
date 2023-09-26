package com.programmers.mediator;

import com.programmers.mediator.dto.Request;
import com.programmers.mediator.dto.Response;

public interface RequestProcessor<T,R>{
    Request<T> getRequest();
    Response<R> processRequest(Request<T> request);
    void sendResponse(Response<R> response);
}