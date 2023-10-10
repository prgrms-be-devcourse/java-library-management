package com.programmers.mediator.dto;

import java.util.Optional;

public class ConsoleRequest<T> implements Request<T> {

    private final T body;
    private final String pathInfo;

    private ConsoleRequest(T body, String pathInfo) {
        this.body = body;
        this.pathInfo = pathInfo;
    }

    @Override
    public Optional<T> getBody() {
        return Optional.ofNullable(body);
    }

    @Override
    public String getPathInfo() {
        return pathInfo;
    }

    public static <T> ConsoleRequest<T> withBodyRequest(T body, String pathInfo) {
        return new ConsoleRequest<>(body, pathInfo);
    }

    public static <T> ConsoleRequest<T> noBodyRequest(String pathInfo) {
        return new ConsoleRequest<>(null, pathInfo);
    }
}
