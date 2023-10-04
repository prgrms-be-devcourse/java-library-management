package com.programmers.infrastructure.IO;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.programmers.exception.checked.InputValidationException;
import com.programmers.infrastructure.IO.validator.InputValidator;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ConsoleTest {

    private Console console;

    private InputValidator validator;

    private Scanner scanner = new Scanner(System.in);
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        validator = Mockito.mock(InputValidator.class);
        console = new Console(scanner, validator);
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testDisplayMessage() {
        String message = "Test Message";
        console.displayMessage(message);

        assertEquals("Test Message\n", outContent.toString());
    }

    @Test
    void testCollectUserInput_Valid() throws InputValidationException {
        String input = "Test Input";
        InputStream testInput = new ByteArrayInputStream(input.getBytes());
        Scanner testScanner = new Scanner(testInput);
        Console testConsole = new Console(testScanner,validator);

        assertEquals("Test Input", testConsole.collectUserInput());
    }

    @Test
    void testCollectUserLongInput_Valid() {
        String input = "12345\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Console testConsole = new Console(new Scanner(input),validator);
        assertEquals(12345L, testConsole.collectUserLongInput());
    }

    @Test
    void testCollectUserIntegerInput_Valid() {
        String input = "123\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Console testConsole = new Console(new Scanner(input),validator);
        assertEquals(123, testConsole.collectUserIntegerInput());
    }

}
