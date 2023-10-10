package com.programmers.infrastructure.IO;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.programmers.BookEntities;
import com.programmers.config.DependencyInjector;
import com.programmers.domain.dto.BookResponse;
import com.programmers.domain.dto.RegisterBookReq;
import com.programmers.util.IdGenerator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class ConsoleInteractionAggregatorTest {

    private ConsoleInteractionAggregator aggregator;
    private Console consoleMock;

    private AutoCloseable closeable;

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private DependencyInjector dependencyInjector = DependencyInjector.getInstance();

    BookEntities bookEntities;

    @BeforeEach
    void setUp() {
        bookEntities = new BookEntities();
        consoleMock = Mockito.mock(Console.class);
        aggregator = new ConsoleInteractionAggregator(consoleMock);
    }

    @Test
    void collectBookInfoInput() {
        when(consoleMock.collectUserInput()).thenReturn("Test Title", "Test Author");
        when(consoleMock.collectUserIntegerInput()).thenReturn(100);

        RegisterBookReq result = aggregator.collectBookInfoInput();

        assertEquals("Test Title", result.getTitle());
        assertEquals("Test Author", result.getAuthor());
        assertEquals(100, result.getPages());
    }

    @Test
    void collectSearchInput() {
        when(consoleMock.collectUserInput()).thenReturn("Test Search");
        String result = aggregator.collectSearchInput();

        assertEquals("Test Search", result);
    }

    @Test
    void collectModeInput() {
        when(consoleMock.collectUserInput()).thenReturn("Test Mode");
        String result = aggregator.collectModeInput();
        assertEquals("Test Mode", result);
    }

    @Test
    void collectMenuInput() {
        when(consoleMock.collectUserInput()).thenReturn("Test Menu");
        String result = aggregator.collectMenuInput();
        assertEquals("Test Menu", result);
    }

    @Test
    void collectDeleteInput() {
        when(consoleMock.collectUserLongInput()).thenReturn(1L);
        Long result = aggregator.collectDeleteInput();
        assertEquals(1L, result);
    }

    @Test
    void collectRentInput() {
        when(consoleMock.collectUserLongInput()).thenReturn(2L);
        Long result = aggregator.collectRentInput();
        assertEquals(2L, result);
    }

    @Test
    void collectReturnInput() {
        when(consoleMock.collectUserLongInput()).thenReturn(3L);
        Long result = aggregator.collectReturnInput();
        assertEquals(3L, result);
    }

    @Test
    void collectLostInput() {
        when(consoleMock.collectUserLongInput()).thenReturn(4L);
        Long result = aggregator.collectLostInput();
        assertEquals(4L, result);
    }

    @Test
    void collectExitInput() {
        when(consoleMock.collectUserInput()).thenReturn("n");
        String result = aggregator.collectExitInput();
        assertEquals("n", result);
    }

    @Test
    void testDisplayListInfo() {
        List<BookResponse> items = bookEntities.getBooksResponses();

        aggregator.displayListInfo(items);
    }

    @Test
    void testDisplaySingleInfo() {
        BookResponse item = bookEntities.getBookResponse(1L);

        assertDoesNotThrow(() -> aggregator.displaySingleInfo(item));
    }

    @Test
    void testDisplayMessage() {
        String message = "Test Message";

        assertDoesNotThrow(() -> aggregator.displayMessage(message));
    }

}