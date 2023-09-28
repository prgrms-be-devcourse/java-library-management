package com.programmers.library.view;


import com.programmers.library.view.console.ConsoleOutput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutputTest {
    PrintStream printStream = System.out;
    ByteArrayOutputStream outputStream;
    ConsoleOutput output;

    @BeforeEach
    public void setUp(){
        output = new ConsoleOutput();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown(){
        System.setOut(printStream);
    }

    @Test
    @DisplayName("콘솔 출력 테스트")
    public void printMessageTest(){
        // Given
        String message = "테스트 메세지";

        // When
        output.write(message);
        String printMessage = outputStream.toString();

        // Then
        assertEquals(message, printMessage);
    }
}
