package com.programmers.infrastructure.IO.command;

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

class ReportLostBookRequestGeneratorTest {
    private AutoCloseable closeable;
    @InjectMocks
    private ReportLostBookRequestGenerator reportLostBookRequestGenerator;
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
        Long expectedLostInput = 1L;
        when(consoleInteractionAggregator.collectLostInput()).thenReturn(expectedLostInput);

        Request request = reportLostBookRequestGenerator.generateRequest();

        assertEquals(expectedLostInput, request.getBody().get());
        assertEquals(Menu.REPORT_LOST_BOOK.getOptionNumber(), request.getPathInfo());
    }
}