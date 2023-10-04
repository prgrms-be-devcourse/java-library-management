package com.programmers.config.enums;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.programmers.config.factory.ModeAbstractFactory;
import com.programmers.config.factory.NormalModeFactory;
import com.programmers.config.factory.TestModeFactory;
import com.programmers.exception.checked.InvalidModeNumberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Mode Enum 테스트")
public class ModeTest {

    @Test
    @DisplayName("모드 가져오기 - [유효한 input]")
    void returnCorrectModeFactoryForValidModeNumbers() throws InvalidModeNumberException {
        ModeAbstractFactory normalFactory = Mode.getModeFactory("1");
        ModeAbstractFactory testFactory = Mode.getModeFactory("2");

        assertTrue(normalFactory instanceof NormalModeFactory);
        assertTrue(testFactory instanceof TestModeFactory);
    }

    @Test
    @DisplayName("모드 가져오기 - [유효하지 않은 input]")
    void shouldThrowExceptionForInvalidModeNumbers() {
        assertThrows(InvalidModeNumberException.class, () -> Mode.getModeFactory("3"));
        assertThrows(InvalidModeNumberException.class, () -> Mode.getModeFactory("abc"));
    }
}

