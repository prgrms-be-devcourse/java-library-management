package com.programmers.mediator;

import com.programmers.adapter.BookControllerAdapter;
import com.programmers.domain.entity.Book;
import com.programmers.presentation.enums.Menu;
import com.programmers.exception.checked.InvalidMenuNumberException;
import com.programmers.infrastructure.IO.ConsoleInteractionAggregator;
import com.programmers.infrastructure.MenuRequestProvider;
import com.programmers.mediator.dto.Request;
import com.programmers.mediator.dto.Response;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ConsoleRequestProcessor implements RequestProcessor<String, String> {

    private final BookControllerAdapter bookControllerAdapter;
    private final ConsoleInteractionAggregator consoleInteractionAggregator;
    private final MenuRequestProvider menuRequestProvider;

    @Override
    public Request getRequest() {
        // 모드 선택 받아서 리퀘스트 만들기까지.
        try {
            return menuRequestProvider.getMenuRequest(
                consoleInteractionAggregator.collectMenuInput());
        } catch (InvalidMenuNumberException e) {
            consoleInteractionAggregator.displayMessage(e.getMessage());
            return getRequest();
        }
    }

    @Override
    public void sendResponse(Response response) {
        response.getBody()
            .ifPresent(body -> consoleInteractionAggregator.displayBooksInfo((List<Book>) body));
        consoleInteractionAggregator.displayMessage(response.getMessage());
    }

    @Override
    public Response processRequest(Request request) {
        try {
            return Menu.routeToController(request, bookControllerAdapter);
        } catch (InvalidMenuNumberException e) {
            consoleInteractionAggregator.displayMessage(e.getMessage());
            return processRequest(getRequest());
        }
    }

}
