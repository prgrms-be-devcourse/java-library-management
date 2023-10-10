package com.programmers.mediator.dto;

public class ConsoleNoBodyResponse extends ConsoleResponse<String>{

    ConsoleNoBodyResponse(String message) {
        super(message);
    }

    public static ConsoleResponse<String> noBodyResponse(String message) {
        return new ConsoleNoBodyResponse(message);
    }
}
