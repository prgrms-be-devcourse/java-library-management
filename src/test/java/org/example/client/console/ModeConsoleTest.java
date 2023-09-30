package org.example.client.console;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ModeConsoleTest {

    @Test
    @DisplayName("모드 설정 입력에서 숫자 외 입력값 예외처리")
    void checkNumberValidationExcaption() {
        Assertions.assertThrows(ValidateException.class, () -> {
            Validator.validateSelectNum(ModeConsole.ModeType.values().length, "%%");
        });
    }

    @Test
    @DisplayName("모드 설정 입력에서 모드 번호 외 입력값 예외처리")
    void checkModeNumberValidationExcaption() {
        Assertions.assertThrows(ValidateException.class, () -> {
            Validator.validateSelectNum(ModeConsole.ModeType.values().length, "100");
        });
    }
}