package com.programmers.infrastructure.IO.requestCommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;

import com.programmers.infrastructure.IO.ConsoleInteractionAggregator;
import com.programmers.mediator.dto.Request;
import com.programmers.presentation.enums.Menu;
import com.programmers.util.Messages;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ViewAllBooksRequestGeneratorTest {
    private AutoCloseable closeable;
    @InjectMocks
    private ViewAllBooksRequestGenerator viewAllBooksRequestGenerator;
    @Mock
    private ConsoleInteractionAggregator consoleInteractionAggregator;

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
    @DisplayName("generateRequest 메서드 검증")
    void generateRequest() {
        Request request = viewAllBooksRequestGenerator.generateRequest();

        assertFalse(request.getBody().isPresent());
        assertEquals(Menu.VIEW_ALL_BOOKS.getOptionNumber(), request.getPathInfo());

        verify(consoleInteractionAggregator).displayMessage(Messages.SELECT_MENU_LIST.getMessage());
    }
}