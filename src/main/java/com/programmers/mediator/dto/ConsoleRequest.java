package com.programmers.mediator.dto;

import lombok.Builder;

public class ConsoleRequest implements Request<String> {
    private final String request;

    @Builder
    private ConsoleRequest(String request) {
        this.request = request;
    }

    @Override
    public String getRequest() {
        return request;
    }

    public static ConsoleRequest of(String request) {
        return ConsoleRequest.builder()
                .request(request)
                .build();
    }
}
