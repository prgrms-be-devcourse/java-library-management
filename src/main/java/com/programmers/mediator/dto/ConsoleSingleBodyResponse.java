package com.programmers.mediator.dto;

public class ConsoleSingleBodyResponse<T> extends ConsoleResponse<T>{

    ConsoleSingleBodyResponse(String message, T body) {
        super(message, body);
    }

    public static <T> ConsoleResponse<T> withBodyResponse(String message, T body) {
        return new ConsoleSingleBodyResponse<>(message, body);
    }

}
