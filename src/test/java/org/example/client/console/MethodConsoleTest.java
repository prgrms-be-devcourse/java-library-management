package org.example.client.console;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MethodConsoleTest {

    @Test
    @DisplayName("")
    void scanTypeAndInfo() {

    }

    @Test
    void setClientMethod() {
        Assertions.assertThrows(ValidateException.class, () -> {
            Validator.validateSelectNum(ModeConsole.ModeType.values().length, "%%");
        });
    }

    @Test
    void scanAndSetBookInfo() {
    }

    @Test
    void scanAndSetBookName() {
    }

    @Test
    void scanAndSetBookId() {
    }
}