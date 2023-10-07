package com.programmers.infrastructure.IO.requestCommand;

import com.programmers.infrastructure.IO.ConsoleInteractionAggregator;
import com.programmers.mediator.dto.ConsoleRequest;
import com.programmers.mediator.dto.Request;
import com.programmers.presentation.enums.Menu;
import com.programmers.util.Messages;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ViewAllBooksRequestGenerator implements MenuRequestGenerator {

    private final ConsoleInteractionAggregator consoleInteractionAggregator;

    @Override
    public String getMenuNumber() {
        return Menu.VIEW_ALL_BOOKS.getOptionNumber();
    }

    @Override
    public Request generateRequest() {
        consoleInteractionAggregator.displayMessage(Messages.SELECT_MENU_LIST.getMessage());
        return ConsoleRequest.noBodyRequest(getMenuNumber());
    }
}
