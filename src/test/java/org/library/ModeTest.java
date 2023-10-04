package org.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.library.controller.ModeController;
import org.library.exception.InvalidModeException;
import org.library.utils.ConsoleInputManager;

import static org.assertj.core.api.Assertions.*;

public class ModeTest {

    private ModeController modeController;
    private ConsoleInputManager consoleInputManager;
    private String path = "src/test/resources/Book.json";

    @BeforeEach
    void init(){
        modeController = new ModeController(consoleInputManager, path);
    }

    @DisplayName("없는 모드를 입력할 시")
    @Test
    void 없는_모드_입력(){
        // given
        int mode = 33;

        //then
        assertThatThrownBy(()-> modeController.getRepository(mode)).isInstanceOf(InvalidModeException.class);
    }
}
