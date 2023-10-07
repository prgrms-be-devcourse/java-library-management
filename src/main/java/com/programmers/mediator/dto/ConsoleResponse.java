package com.programmers.mediator.dto;

import java.util.Optional;

public abstract class ConsoleResponse<T> implements Response<T> {

    private final String message;
    private final T body;

    ConsoleResponse(String message, T body) {
        this.message = message;
        this.body = body;
    }

    ConsoleResponse(String message) {
        this.message = message;
        this.body = null;
    }

    @Override
    public Optional<T> getBody() {
        return Optional.ofNullable(body);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
