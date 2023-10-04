package com.programmers.infrastructure.IO.command;

import com.programmers.presentation.enums.Menu;
import com.programmers.infrastructure.IO.ConsoleInteractionAggregator;
import com.programmers.mediator.dto.ConsoleRequest;
import com.programmers.mediator.dto.Request;
import com.programmers.util.Messages;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterBookRequestGenerator implements MenuRequestGenerator {

    private final ConsoleInteractionAggregator consoleInteractionAggregator;

    @Override
    public String getMenuNumber() {
        return Menu.REGISTER_BOOK.getOptionNumber();
    }

    @Override
    public Request generateRequest() {
        consoleInteractionAggregator.displayMessage(Messages.SELECT_MENU_REGISTER.getMessage());
        return ConsoleRequest.withBodyRequest(
            consoleInteractionAggregator.collectBookInfoInput(), getMenuNumber());
    }
}
