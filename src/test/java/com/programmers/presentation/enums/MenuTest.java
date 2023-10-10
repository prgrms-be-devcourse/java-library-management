package com.programmers.presentation.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.programmers.adapter.BookControllerAdapter;
import com.programmers.exception.checked.InvalidMenuNumberException;
import com.programmers.mediator.dto.ConsoleRequest;
import com.programmers.mediator.dto.ConsoleResponse;
import com.programmers.mediator.dto.Request;
import com.programmers.mediator.dto.Response;
import com.programmers.presentation.BookController;
import org.junit.jupiter.api.Test;

class MenuTest {

    private final BookControllerAdapter mockControllerAdapter = mock(BookControllerAdapter.class);
    private final BookController mockController = mock(BookController.class);
    private final Object[] params = new Object[]{"Test Title", "Test Author", 100};

    @Test
    void testExecute() {
        Response mockResponse = mock(ConsoleResponse.class);
        when(mockControllerAdapter.registerBook(params[0])).thenReturn((ConsoleResponse) mockResponse);

        Response response = Menu.REGISTER_BOOK.execute(mockControllerAdapter, params[0]);
        assertEquals(mockResponse, response);
    }

    @Test
    void testRouteToController_ValidMenuOptionWithBody() throws InvalidMenuNumberException {
        Response mockResponse = mock(ConsoleResponse.class);
        when(mockControllerAdapter.searchBooksByTitle(params[0])).thenReturn((ConsoleResponse) mockResponse);

        Request request = ConsoleRequest.withBodyRequest(params[0],"3");
        Response response = Menu.routeToController(request, mockControllerAdapter);
        assertEquals(mockResponse, response);
    }

    @Test
    void testRouteToController_ValidMenuOptionWithoutBody() throws InvalidMenuNumberException {
        Response mockResponse = mock(ConsoleResponse.class);
        when(mockControllerAdapter.exitApplication()).thenReturn((ConsoleResponse) mockResponse);

        Request request = ConsoleRequest.noBodyRequest("0");
        Response response = Menu.routeToController(request, mockControllerAdapter);
        assertEquals(mockResponse, response);
    }

    @Test
    void testRouteToController_InvalidMenuOption() {
        Request invalidRequest = ConsoleRequest.noBodyRequest("999");
        assertThrows(InvalidMenuNumberException.class, () -> Menu.routeToController(invalidRequest, mockControllerAdapter));
    }
}
