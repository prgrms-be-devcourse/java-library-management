package com.programmers.mediator.dto;

import lombok.Builder;

public class ConsoleResponse implements Response<String>{
    private final String response;

    @Builder
    private ConsoleResponse(String response) {
        this.response = response;
    }

    @Override
    public String getResponse() {
        return response;
    }

    public static ConsoleResponse of(String response) {
        return ConsoleResponse.builder()
                .response(response)
                .build();
    }
}
