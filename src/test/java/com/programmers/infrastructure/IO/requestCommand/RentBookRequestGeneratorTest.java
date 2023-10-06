package com.programmers.infrastructure.IO.requestCommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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

class RentBookRequestGeneratorTest {
    private AutoCloseable closeable;
    @InjectMocks
    private RentBookRequestGenerator rentBookRequestGenerator;
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
        Long expectedRentInput = 1L;
        when(consoleInteractionAggregator.collectRentInput()).thenReturn(expectedRentInput);

        Request request = rentBookRequestGenerator.generateRequest();

        assertEquals(expectedRentInput, request.getBody().get());
        assertEquals(Menu.RENT_BOOK.getOptionNumber(), request.getPathInfo());
    }
}