package com.programmers.infrastructure.IO.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.programmers.domain.dto.RegisterBookReq;
import com.programmers.infrastructure.IO.ConsoleInteractionAggregator;
import com.programmers.mediator.dto.Request;
import com.programmers.presentation.enums.Menu;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class RegisterBookRequestGeneratorTest {
    private AutoCloseable closeable;
    @InjectMocks
    private RegisterBookRequestGenerator registerBookRequestGenerator;
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
        RegisterBookReq expectedBookInfoInput = RegisterBookReq.from("Test Title", "Test Author", 123);
        when(consoleInteractionAggregator.collectBookInfoInput()).thenReturn(expectedBookInfoInput);

        Request request = registerBookRequestGenerator.generateRequest();

        assertEquals(expectedBookInfoInput, request.getBody().get());
        assertEquals(Menu.REGISTER_BOOK.getOptionNumber(), request.getPathInfo());
    }
}