package com.programmers.mediator.dto;

import java.util.Optional;

public class ConsoleResponse<T> implements Response<T> {

    private final String message;
    private final T body;

    private ConsoleResponse(String message, T body) {
        this.message = message;
        this.body = body;
    }

    private ConsoleResponse(String message) {
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

    public static <T> ConsoleResponse<T> noBodyResponse(String message) {
        return new ConsoleResponse<>(message);
    }

    public static <T> ConsoleResponse<T> withBodyResponse(String message, T body) {
        return new ConsoleResponse<>(message, body);
    }
}
