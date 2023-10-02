package com.programmers.library.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class InputTest {

    InputStream inputStream = System.in;

    @AfterEach
    public void tearDown(){
        System.setIn(inputStream);
    }

    @Test
    @DisplayName("사용자 숫자 입력 확인 테스트")
    public void inputNumberTest(){
        // Given
        String userInput = "1\n";

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(byteArrayInputStream);

        ConsoleInput input = new ConsoleInput();

        // When
        Long number = input.selectNumber();

        // Then
        assertEquals(1L, number);
    }

    @Test
    @DisplayName("사용자 문자 입력 확인 테스트")
    public void inputStringTest(){
        // Given
        String userInput = "스킨 인 더 게임\n";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(inputStream);

        ConsoleInput input = new ConsoleInput();

        // When
        String result = input.inputString();

        // Then
        assertEquals("스킨 인 더 게임", result);
    }
}