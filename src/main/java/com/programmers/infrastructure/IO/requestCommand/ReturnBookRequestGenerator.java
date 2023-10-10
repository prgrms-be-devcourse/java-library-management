package com.programmers.infrastructure.IO.requestCommand;

import com.programmers.infrastructure.IO.ConsoleInteractionAggregator;
import com.programmers.mediator.dto.ConsoleRequest;
import com.programmers.mediator.dto.Request;
import com.programmers.presentation.enums.Menu;
import com.programmers.util.Messages;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReturnBookRequestGenerator implements MenuRequestGenerator {

    private final ConsoleInteractionAggregator consoleInteractionAggregator;

    @Override
    public String getMenuNumber() {
        return Menu.RETURN_BOOK.getOptionNumber();
    }

    @Override
    public Request generateRequest() {
        consoleInteractionAggregator.displayMessage(Messages.SELECT_MENU_RETURN.getMessage());
        return ConsoleRequest.withBodyRequest(
            consoleInteractionAggregator.collectRentInput(), getMenuNumber());
    }
}
