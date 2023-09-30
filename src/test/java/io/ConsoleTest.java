package io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.GeneralService;
import service.Service;
import service.TestService;

import static org.assertj.core.api.Assertions.*;


class ConsoleTest {

    Service printMode(int mode) {
        if (mode == 1) {
             return new GeneralService();
        } else if (mode == 2) {
            return new TestService();
        } else {
            throw new RuntimeException("[System] 잘못된 입력입니다.");
        }
    }

    @Test
    @DisplayName("일반모드 1 테스트")
    void isRightInput1() {
        int input = 1;

        assertThat(printMode(input)).isInstanceOf(GeneralService.class);
    }

    @Test
    @DisplayName("테스트모드 2 테스트")
    void isRightInput2() {
        int input = 2;

        assertThat(printMode(input)).isInstanceOf(TestService.class);
    }

    @Test
    @DisplayName("잘못된 입력 테스트")
    void isBadInput() {
        int input = 111;

        assertThatThrownBy(() -> printMode(input))
                .isInstanceOf(RuntimeException.class);
    }
}