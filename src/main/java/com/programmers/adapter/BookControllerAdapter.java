package com.programmers.adapter;

import com.programmers.domain.dto.RegisterBookReq;
import com.programmers.mediator.dto.ConsoleListBodyResponse;
import com.programmers.mediator.dto.ConsoleNoBodyResponse;
import com.programmers.mediator.dto.ConsoleResponse;
import com.programmers.mediator.dto.ExitResponse;
import com.programmers.presentation.BookController;
import com.programmers.presentation.enums.ExitCommand;
import com.programmers.util.Messages;

// 프레젠테이션에 잇어야하지 ㅇ낳을까
public class BookControllerAdapter {

    private final BookController controller;

    public BookControllerAdapter(BookController controller) {
        this.controller = controller;
    }

    public ConsoleResponse exitApplication(Object... params) {
        String request = (String) params[0];
        boolean result = ExitCommand.promptForExit(request);

        //TODO: 오버로딩
        return ExitResponse.noBodyResponse(result);
    }

    public ConsoleResponse registerBook(Object... params) {
        RegisterBookReq request = (RegisterBookReq) params[0];
        controller.registerBook(request);
        return ConsoleNoBodyResponse.noBodyResponse(Messages.BOOK_REGISTER_SUCCESS.getMessage());
    }

    public ConsoleResponse getAllBooks(Object... params) {
        return ConsoleListBodyResponse.withBodyResponse(Messages.BOOK_LIST_SUCCESS.getMessage(),
            controller.getAllBooks());
    }

    public ConsoleResponse searchBooksByTitle(Object... params) {
        String title = (String) params[0];
        return ConsoleListBodyResponse.withBodyResponse(Messages.BOOK_SEARCH_SUCCESS.getMessage(),
            controller.searchBooksByTitle(title));
    }

    public ConsoleResponse rentBook(Object... params) {
        Long bookId = (Long) params[0];
        controller.rentBook(bookId);
        return ConsoleNoBodyResponse.noBodyResponse(Messages.BOOK_RENT_SUCCESS.getMessage());
    }

    public ConsoleResponse returnBook(Object... params) {
        Long bookId = (Long) params[0];
        controller.returnBook(bookId);
        return ConsoleNoBodyResponse.noBodyResponse(Messages.BOOK_RETURN_SUCCESS.getMessage());
    }

    public ConsoleResponse reportLostBook(Object... params) {
        Long bookId = (Long) params[0];
        controller.reportLostBook(bookId);
        return ConsoleNoBodyResponse.noBodyResponse(Messages.BOOK_LOST_SUCCESS.getMessage());
    }

    public ConsoleResponse deleteBook(Object... params) {
        Long bookId = (Long) params[0];
        controller.deleteBook(bookId);
        return ConsoleNoBodyResponse.noBodyResponse(Messages.BOOK_DELETE_SUCCESS.getMessage());
    }
}

