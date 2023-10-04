package com.programmers.infrastructure.IO.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

class DeleteBookRequestGeneratorTest {
    private AutoCloseable closeable;
    @InjectMocks
    private DeleteBookRequestGenerator deleteBookRequestGenerator;
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
    void testGenerateRequest() {
        Long expectedDeleteInput = 1L;
        when(consoleInteractionAggregator.collectDeleteInput()).thenReturn(expectedDeleteInput);

        Request request = deleteBookRequestGenerator.generateRequest();

        verify(consoleInteractionAggregator).displayMessage(Messages.SELECT_MENU_DELETE.getMessage());
        assertEquals(expectedDeleteInput, request.getBody().get());
        assertEquals(Menu.DELETE_BOOK.getOptionNumber(), request.getPathInfo());
    }
}
