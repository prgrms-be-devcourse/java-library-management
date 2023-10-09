package org.example.client.console;

import org.example.client.ValidateException;
import org.example.client.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ValidatorTest {
    Validator validator;

    int selectNum;

    @BeforeEach
    @DisplayName("Validator 초기화")
    void initValidator() {
        validator = new Validator();
        selectNum = 5;
    }

    /* 문자 입력 유효성 테스트 */
    @DisplayName("문자열 유효성 검증: 성공")
    @ParameterizedTest
    @ValueSource(strings = {"A B C", "a b c", "가나다"})
    void validateNameAndAuthor(String input) {
        Assertions.assertEquals(validator.scanAndValidateNameAndAuthor(input), input);
    }

    @DisplayName("문자열 유효성 검증: 특수 문자 예외")
    @ParameterizedTest
    @ValueSource(strings = {"%", "$", "*"})
    void validateNameAndAuthorSpecial(String input) {
        Assertions.assertThrows(ValidateException.class, () -> validator.scanAndValidateNameAndAuthor(input));
    }

    @DisplayName("문자열 유효성 검증: 공백 예외")
    @ParameterizedTest
    @EmptySource
    void validateNameAndAuthorEmpty(String input) {
        Assertions.assertThrows(ValidateException.class, () -> validator.scanAndValidateNameAndAuthor(input));
    }

    @DisplayName("문자열 유효성 검증: 문자열 길이 100자 이상 예외")
    @Test
    void validateNameAndAuthorLength() {
        String input = "a".repeat(120);

        Assertions.assertThrows(ValidateException.class, () -> validator.scanAndValidateNameAndAuthor(input));
    }

    /* 숫자 입력 유효성 테스트 */
    @DisplayName("숫자 유효성 검증: 성공")
    @ParameterizedTest
    @ValueSource(strings = {"50", "100", "1000"})
    void validateIdAndPages(String input) {
        Assertions.assertEquals(validator.scanAndValidateIdAndPageNumber(input), Integer.parseInt(input));
    }

    @DisplayName("숫자 유효성 검증: 공백 예외")
    @ParameterizedTest
    @EmptySource
    void validateIdAndPagesEmpty(String input) {
        Assertions.assertThrows(ValidateException.class, () -> validator.scanAndValidateIdAndPageNumber(input));
    }

    @DisplayName("숫자 유효성 검증: 숫자가 아닌 특수문자 예외")
    @ParameterizedTest
    @ValueSource(strings = {"%", "$", "*"})
    void validateIdAndPagesSpecial(String input) {
        Assertions.assertThrows(ValidateException.class, () -> validator.scanAndValidateIdAndPageNumber(input));
    }

    @DisplayName("숫자 유효성 검증: 숫자가 아닌 문자열(한글/영어) 예외")
    @ParameterizedTest
    @ValueSource(strings = {"ABC", "abc", "가나다"})
    void validateIdAndPagesAlpha(String input) {
        Assertions.assertThrows(ValidateException.class, () -> validator.scanAndValidateIdAndPageNumber(input));
    }

    @DisplayName("숫자 유효성 검증: 0 이하 숫자 범위 예외")
    @ParameterizedTest
    @ValueSource(strings = {"-1", "0", "-500"})
    void validateIdAndPagesDown1(String input) {
        Assertions.assertThrows(ValidateException.class, () -> validator.scanAndValidateIdAndPageNumber(input));
    }

    @DisplayName("숫자 유효성 검증: 5000 이상 숫자 범위 예외")
    @ParameterizedTest
    @ValueSource(strings = {"5000", "100000", "50000"})
    void validateIdAndPagesUP5000(String input) {
        Assertions.assertThrows(ValidateException.class, () -> validator.scanAndValidateIdAndPageNumber(input));
    }

    /* 모드/메뉴 번호 입력 유효성 테스트 */
    @DisplayName("모드/메뉴 번호(5가지라고 가정) 입력 유효성 검증: 성공")
    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3"})
    void validateSelectNum(String input) {
        Assertions.assertEquals(validator.scanAndValidateSelectionNumber(selectNum, input), Integer.parseInt(input));
    }

    @DisplayName("모드/메뉴 번호(5가지라고 가정) 입력 유효성 검증: 공백 예외")
    @ParameterizedTest
    @EmptySource
    void validateSelectNumEmpty(String input) {
        Assertions.assertThrows(ValidateException.class, () -> validator.scanAndValidateSelectionNumber(selectNum, input));
    }

    @DisplayName("모드/메뉴 번호(5가지라고 가정) 입력 유효성 검증: 숫자가 아닌 특수문자 예외")
    @ParameterizedTest
    @ValueSource(strings = {"%", "$", "*"})
    void validateSelectNumSpecial(String input) {
        Assertions.assertThrows(ValidateException.class, () -> validator.scanAndValidateSelectionNumber(selectNum, input));
    }

    @DisplayName("모드/메뉴 번호(5가지라고 가정) 입력 유효성 검증: 숫자가 아닌 문자열(한글/영어) 예외")
    @ParameterizedTest
    @ValueSource(strings = {"ABC", "abc", "가나다"})
    void validateSelectNumAlpha(String input) {
        Assertions.assertThrows(ValidateException.class, () -> validator.scanAndValidateSelectionNumber(selectNum, input));
    }

    @DisplayName("모드/메뉴 번호 입력(5가지라고 가정) 유효성 검증: 0 이하 숫자 범위 예외")
    @ParameterizedTest
    @ValueSource(strings = {"-1", "0", "-500"})
    void validateSelectNumDown1(String input) {
        Assertions.assertThrows(ValidateException.class, () -> validator.scanAndValidateSelectionNumber(selectNum, input));
    }

    @DisplayName("모드/메뉴 번호 입력(5가지라고 가정) 유효성 검증: 선택 번호이상 숫자 범위 예외")
    @ParameterizedTest
    @ValueSource(strings = {"6", "5000", "123"})
    void validateSelectNumUP5000(String input) {
        Assertions.assertThrows(ValidateException.class, () -> validator.scanAndValidateSelectionNumber(selectNum, input));
    }
}