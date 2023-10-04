package com.programmers.mediator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.programmers.adapter.BookControllerAdapter;
import com.programmers.config.DependencyInjector;
import com.programmers.domain.entity.Book;
import com.programmers.domain.enums.BookStatus;
import com.programmers.exception.checked.InvalidMenuNumberException;
import com.programmers.infrastructure.IO.ConsoleInteractionAggregator;
import com.programmers.infrastructure.MenuRequestProvider;
import com.programmers.mediator.dto.ConsoleResponse;
import com.programmers.mediator.dto.Request;
import com.programmers.mediator.dto.Response;
import com.programmers.util.IdGenerator;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ConsoleRequestProcessorTest {
    private AutoCloseable closeable;

    @InjectMocks
    private ConsoleRequestProcessor processor;
    @Mock
    private BookControllerAdapter bookControllerAdapterMock;
    @Mock
    private ConsoleInteractionAggregator consoleInteractionAggregatorMock;
    @Mock
    private MenuRequestProvider menuRequestProviderMock;

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private DependencyInjector dependencyInjector = DependencyInjector.getInstance();

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (closeable != null) {
            closeable.close();
        }
    }

    @Test
    void getRequest_ValidMenu() throws InvalidMenuNumberException {
        String validMenuInput = "1";
        Request mockRequest = mock(Request.class);

        when(consoleInteractionAggregatorMock.collectMenuInput()).thenReturn(validMenuInput);
        when(menuRequestProviderMock.getMenuRequest(validMenuInput)).thenReturn(mockRequest);

        processor.getRequest();

        verify(consoleInteractionAggregatorMock).collectMenuInput();
        verify(menuRequestProviderMock).getMenuRequest(validMenuInput);
    }

    @Test
    void sendResponse_WithBooksBody() {
        when(idGenerator.generateId()).thenReturn(1L);
        List<Book> books = Collections.singletonList(Book.builder()
            .id(1L)
            .title("Test Title")
            .author("Test Author")
            .pages(100)
            .status(BookStatus.AVAILABLE)
            .build());
        Response response = ConsoleResponse.withBodyResponse("Test Message", books);

        processor.sendResponse(response);

        verify(consoleInteractionAggregatorMock).displayBooksInfo(books);
        verify(consoleInteractionAggregatorMock).displayMessage("Test Message");
    }

    @Test
    void testSendResponse_WithoutBooksBody() {

        Response response = ConsoleResponse.noBodyResponse("Test Message");

        processor.sendResponse(response);

        verify(consoleInteractionAggregatorMock, never()).displayBooksInfo(any());
        verify(consoleInteractionAggregatorMock).displayMessage("Test Message");
    }
}
