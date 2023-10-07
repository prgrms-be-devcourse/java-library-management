package com.programmers.mediator.dto;

import java.util.List;

public class ConsoleListBodyResponse extends ConsoleResponse<List> {

    ConsoleListBodyResponse(String message, List body) {
        super(message, body);
    }

    public static ConsoleResponse<List> withBodyResponse(String message, List body) {
        return new ConsoleListBodyResponse(message, body);
    }

}
