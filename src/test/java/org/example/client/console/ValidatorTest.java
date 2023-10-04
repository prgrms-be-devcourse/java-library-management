package org.example.client.console;

import org.example.client.ValidateException;
import org.example.client.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ValidatorTest {
    /* 문자 입력 유효성 테스트 */
    @Test
    @DisplayName("문자열 유효성 검증: 특수 문자 예외")
    void validateNameAndAuthorSpecial() {
        Assertions.assertThrows(ValidateException.class, () -> {
            Validator.validateNameAndAuthor("%");
        });
    }

    @Test
    @DisplayName("문자열 유효성 검증: 공백 예외")
    void validateNameAndAuthorEmpty() {
        Assertions.assertThrows(ValidateException.class, () -> {
            Validator.validateNameAndAuthor("");
        });
    }

    @Test
    @DisplayName("문자열 유효성 검증: 문자열 길이 100자 이상 예외")
    void validateNameAndAuthorLength() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 120; i++) sb.append("a");
        Assertions.assertThrows(ValidateException.class, () -> {
            Validator.validateNameAndAuthor(sb.toString());
        });
    }

    /* 숫자 입력 유효성 테스트 */
    @DisplayName("숫자 유효성 검증: 공백 예외")
    @Test
    void validateIdAndPagesEmpty() {
        Assertions.assertThrows(ValidateException.class, () -> {
            Validator.validateIdAndPages("");
        });
    }

    @DisplayName("숫자 유효성 검증: 숫자가 아닌 문자열(특수문자) 예외")
    @Test
    void validateIdAndPagesSpecial() {
        Assertions.assertThrows(ValidateException.class, () -> {
            Validator.validateIdAndPages("a");
        });
    }

    @DisplayName("숫자 유효성 검증: 숫자가 아닌 문자열(한글/영어) 예외")
    @Test
    void validateIdAndPagesAlpha() {
        Assertions.assertThrows(ValidateException.class, () -> {
            Validator.validateIdAndPages("a");
        });
    }

    @DisplayName("숫자 유효성 검증: 0 이하 숫자 범위 예외")
    @Test
    void validateIdAndPagesDown1() {
        Assertions.assertThrows(ValidateException.class, () -> {
            Validator.validateIdAndPages("0");
        });
    }

    @DisplayName("숫자 유효성 검증: 5000 이상 숫자 범위 예외")
    @Test
    void validateIdAndPagesUP5000() {
        Assertions.assertThrows(ValidateException.class, () -> {
            Validator.validateIdAndPages("5001");
        });
    }

    /* 모드/메뉴 번호 입력 유효성 테스트 */
    @DisplayName("모드/메뉴 번호(5가지라고 가정) 입력 유효성 검증: 공백 예외")
    @Test
    void validateSelectNumEmpty() {
        Assertions.assertThrows(ValidateException.class, () -> {
            Validator.validateSelectNum(5, "");
        });
    }

    @DisplayName("모드/메뉴 번호(5가지라고 가정) 입력 유효성 검증: 숫자가 아닌 문자열(특수문자) 예외")
    @Test
    void validateSelectNum() {
        Assertions.assertThrows(ValidateException.class, () -> {
            Validator.validateSelectNum(5, "%%");
        });
    }

    @DisplayName("모드/메뉴 번호(5가지라고 가정) 입력 유효성 검증: 숫자가 아닌 문자열(한글/영어) 예외")
    @Test
    void validateSelectNumAlpha() {
        Assertions.assertThrows(ValidateException.class, () -> {
            Validator.validateSelectNum(5, "a");
        });
    }

    @DisplayName("모드/메뉴 번호 입력(5가지라고 가정) 유효성 검증: 0 이하 숫자 범위 예외")
    @Test
    void validateSelectNumDown1() {
        Assertions.assertThrows(ValidateException.class, () -> {
            Validator.validateSelectNum(5, "0");
        });
    }

    @DisplayName("모드/메뉴 번호 입력(5가지라고 가정) 유효성 검증: 선택 번호이상 숫자 범위 예외")
    @Test
    void validateSelectNumUP5000() {
        Assertions.assertThrows(ValidateException.class, () -> {
            Validator.validateSelectNum(5, "5001");
        });
    }
}