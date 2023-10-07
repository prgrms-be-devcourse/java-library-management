package org.example.client.console;

import org.example.client.ValidateException;
import org.example.client.Validator;
import org.example.client.io.In;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValidatorTest {
    In in;
    Validator validator;

    int selectNum;

    @BeforeEach
    @DisplayName("Validator 초기화")
    void initValidator() {
        in = mock(In.class);
        validator = new Validator();
        selectNum = 5;
    }

    /* 문자 입력 유효성 테스트 */
    @Test
    @DisplayName("문자열 유효성 검증: 성공")
    void validateNameAndAuthor() {
        String input = "a부터 Z까지 배우는 테스트 코드";
        when(in.scanLine()).thenReturn(input);

        Assertions.assertEquals(validator.scanAndValidateNameAndAuthor(in.scanLine()), input);
    }

    @Test
    @DisplayName("문자열 유효성 검증: 특수 문자 예외")
    void validateNameAndAuthorSpecial() {
        String input = "%";
        when(in.scanLine()).thenReturn(input);

        Assertions.assertThrows(ValidateException.class, () -> {
            validator.scanAndValidateNameAndAuthor(in.scanLine());
        });
    }

    @Test
    @DisplayName("문자열 유효성 검증: 공백 예외")
    void validateNameAndAuthorEmpty() {
        String input = "";
        when(in.scanLine()).thenReturn(input);

        Assertions.assertThrows(ValidateException.class, () -> {
            validator.scanAndValidateNameAndAuthor(in.scanLine());
        });
    }

    @Test
    @DisplayName("문자열 유효성 검증: 문자열 길이 100자 이상 예외")
    void validateNameAndAuthorLength() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 120; i++) sb.append("a");
        String input = sb.toString();

        when(in.scanLine()).thenReturn(input);

        Assertions.assertThrows(ValidateException.class, () -> {
            validator.scanAndValidateNameAndAuthor(in.scanLine());
        });
    }

    /* 숫자 입력 유효성 테스트 */
    @DisplayName("숫자 유효성 검증: 성공")
    @Test
    void validateIdAndPages() {
        String input = "50";
        when(in.scanLine()).thenReturn(input);

        Assertions.assertEquals(validator.scanAndValidateIdAndPageNumber(in.scanLine()), Integer.parseInt(input));
    }

    @DisplayName("숫자 유효성 검증: 공백 예외")
    @Test
    void validateIdAndPagesEmpty() {
        String input = "";
        when(in.scanLine()).thenReturn(input);

        Assertions.assertThrows(ValidateException.class, () -> {
            validator.scanAndValidateIdAndPageNumber(in.scanLine());
        });
    }

    @DisplayName("숫자 유효성 검증: 숫자가 아닌 문자열(특수문자) 예외")
    @Test
    void validateIdAndPagesSpecial() {
        String input = "a";
        when(in.scanLine()).thenReturn(input);

        Assertions.assertThrows(ValidateException.class, () -> {
            validator.scanAndValidateIdAndPageNumber(in.scanLine());
        });
    }

    @DisplayName("숫자 유효성 검증: 숫자가 아닌 문자열(한글/영어) 예외")
    @Test
    void validateIdAndPagesAlpha() {
        String input = "a";
        when(in.scanLine()).thenReturn(input);

        Assertions.assertThrows(ValidateException.class, () -> {
            validator.scanAndValidateIdAndPageNumber(in.scanLine());
        });
    }

    @DisplayName("숫자 유효성 검증: 0 이하 숫자 범위 예외")
    @Test
    void validateIdAndPagesDown1() {
        String input = "0";
        when(in.scanLine()).thenReturn(input);

        Assertions.assertThrows(ValidateException.class, () -> {
            validator.scanAndValidateIdAndPageNumber(in.scanLine());
        });
    }

    @DisplayName("숫자 유효성 검증: 5000 이상 숫자 범위 예외")
    @Test
    void validateIdAndPagesUP5000() {
        String input = "5001";
        when(in.scanLine()).thenReturn(input);

        Assertions.assertThrows(ValidateException.class, () -> {
            validator.scanAndValidateIdAndPageNumber(in.scanLine());
        });
    }

    /* 모드/메뉴 번호 입력 유효성 테스트 */
    @DisplayName("모드/메뉴 번호(5가지라고 가정) 입력 유효성 검증: 성공")
    @Test
    void validateSelectNum() {
        String input = "2";
        when(in.scanLine()).thenReturn(input);

        Assertions.assertEquals(validator.scanAndValidateSelectionNumber(selectNum, in.scanLine()), Integer.parseInt(input));
    }

    @DisplayName("모드/메뉴 번호(5가지라고 가정) 입력 유효성 검증: 공백 예외")
    @Test
    void validateSelectNumEmpty() {
        String input = "";
        when(in.scanLine()).thenReturn(input);

        Assertions.assertThrows(ValidateException.class, () -> {
            validator.scanAndValidateSelectionNumber(selectNum, in.scanLine());
        });
    }

    @DisplayName("모드/메뉴 번호(5가지라고 가정) 입력 유효성 검증: 숫자가 아닌 문자열(특수문자) 예외")
    @Test
    void validateSelectNumSpecial() {
        String input = "%%";
        when(in.scanLine()).thenReturn(input);

        Assertions.assertThrows(ValidateException.class, () -> {
            validator.scanAndValidateSelectionNumber(selectNum, in.scanLine());
        });
    }

    @DisplayName("모드/메뉴 번호(5가지라고 가정) 입력 유효성 검증: 숫자가 아닌 문자열(한글/영어) 예외")
    @Test
    void validateSelectNumAlpha() {
        String input = "a";
        when(in.scanLine()).thenReturn(input);

        Assertions.assertThrows(ValidateException.class, () -> {
            validator.scanAndValidateSelectionNumber(selectNum, in.scanLine());
        });
    }

    @DisplayName("모드/메뉴 번호 입력(5가지라고 가정) 유효성 검증: 0 이하 숫자 범위 예외")
    @Test
    void validateSelectNumDown1() {
        String input = "0";
        when(in.scanLine()).thenReturn(input);

        Assertions.assertThrows(ValidateException.class, () -> {
            validator.scanAndValidateSelectionNumber(selectNum, in.scanLine());
        });
    }

    @DisplayName("모드/메뉴 번호 입력(5가지라고 가정) 유효성 검증: 선택 번호이상 숫자 범위 예외")
    @Test
    void validateSelectNumUP5000() {
        String input = "5001";
        when(in.scanLine()).thenReturn(input);

        Assertions.assertThrows(ValidateException.class, () -> {
            validator.scanAndValidateSelectionNumber(selectNum, in.scanLine());
        });
    }
}