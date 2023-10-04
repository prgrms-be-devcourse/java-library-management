package com.programmers.infrastructure.IO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.programmers.config.DependencyInjector;
import com.programmers.domain.dto.RegisterBookReq;
import com.programmers.domain.entity.Book;
import com.programmers.domain.enums.BookStatus;
import com.programmers.util.IdGenerator;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ConsoleInteractionAggregatorTest {

    private ConsoleInteractionAggregator aggregator;
    private Console consoleMock;

    private AutoCloseable closeable;

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private DependencyInjector dependencyInjector = DependencyInjector.getInstance();

    @BeforeEach
    void setUp() {
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

    // You can similarly add test cases for other collect methods

    @Test
    void displayBooksInfo() {
        closeable = MockitoAnnotations.openMocks(this);
        when(idGenerator.generateId()).thenReturn(1L);

        List<Book> books = Arrays.asList(
            Book.builder().author("Author 1").title("Book 1").pages(600)
                .status(BookStatus.AVAILABLE).build(),
            Book.builder().author("Author 2").title("Book 2").pages(456)
                .status(BookStatus.AVAILABLE).build(),
            Book.builder().author("Author 3").title("Book 3").pages(789)
                .status(BookStatus.AVAILABLE).build()
        );

        aggregator.displayBooksInfo(books);

        verify(consoleMock, times(books.size())).displayMessage(anyString());

        try{
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
    void displayMessage() {
        String message = "Test Message";
        aggregator.displayMessage(message);
        verify(consoleMock).displayMessage(message);
    }

}