package org.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.library.controller.ModeController;
import org.library.exception.InvalidModeException;

import static org.assertj.core.api.Assertions.*;

public class ModeTest {

    private ModeController modeController;

    @BeforeEach
    void init(){
        modeController = new ModeController();
    }

    @DisplayName("없는 모드를 입력할 시")
    @Test
    void 없는_모드_입력(){
        int mode = 33;
        assertThatThrownBy(()-> modeController.getRepository(mode)).isInstanceOf(InvalidModeException.class);
    }
}
