package org.library;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.library.domain.Func;
import org.library.exception.InvalidFuncException;

public class FuncTest {

    @DisplayName("기능에 없는 값을 입력했을 때")
    @Test
    public void 없는_기능번호_입력() {
        //given
        int errorFunc = 99;

        //then
        Assertions.assertThatThrownBy(() -> Func.of(errorFunc))
            .isInstanceOf(InvalidFuncException.class);
    }
}
