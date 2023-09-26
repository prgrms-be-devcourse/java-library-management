package com.programmers.infrastructure.IO;

import com.programmers.domain.enums.MenuCommand;
import com.programmers.mediator.dto.Request;
import com.programmers.mediator.dto.Response;
import com.programmers.mediator.RequestProcessor;
import com.programmers.presentation.ConsoleController;

//TODO: 생성자 주입 고민
//@RequiredArgsConstructor
public class ConsoleRequestProcessor implements RequestProcessor<String,String> {
    private final Console console = Console.getInstance();
    private final ConsoleController controller = new ConsoleController();

    public Request<String> getRequest() {
        console.printToConsole("메뉴 선택 메세지");
        return console::getInput;
    }

    public void sendResponse(Response<String> response) {
        console.printToConsole("성공");
    }

    @Override
    public Response<String> processRequest(Request<String> request) {
        return MenuCommand.executeServiceByOptionNum(request.getRequest(), controller);
    }
}
