package com.example.library.io;

import com.example.library.validation.InputValidator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InputTest {

    @ParameterizedTest
    @CsvSource(value = {"1,1","2,2"})
    @DisplayName("모드 선택 입력값에 1,2만 성공한다.")
    void successModeNumber(String input,int result) {

        int modeNumber = InputValidator.isModeNumber(input);

        assertThat(modeNumber).isEqualTo(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"4","124","abc"})
    @DisplayName("모드 선택 입력값에 1,2를 제외하고 입력하면 실패한다.")
    void failModeNumber(String input) {

        assertThrows(NumberFormatException.class,
                () -> InputValidator.isModeNumber(input));
    }

    @ParameterizedTest
    @CsvSource(value = {"3,3","7,7"})
    @DisplayName("메뉴 선택 입력값에 0~7만 성공한다.")
    void successMenuNumber(String input,int result) {

        int menuNumber = InputValidator.isMenuNumber(input);

        assertThat(menuNumber).isEqualTo(result);
    }
    @ParameterizedTest
    @ValueSource(strings = {"-1","12","a","214124"})
    @DisplayName("모드 선택 입력값에 0~7를 제외하고 입력하면 실패한다.")
    void failMenuNumber(String input) {

        assertThrows(NumberFormatException.class,
                () -> InputValidator.isMenuNumber(input));
    }

}
