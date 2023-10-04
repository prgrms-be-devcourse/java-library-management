package org.example.client.console;

import java.util.regex.Pattern;

public class Validator {
    protected static String validateNameAndAuthor(String input) {
        if (Pattern.matches("[a-zA-Z0-9ㄱ-ㅎ가-힣 ]{1,99}$", input)) {
            return input;
        }
        throw new ValidateException() {
            @Override
            public String getMessage() {
                return System.lineSeparator() + "[System] 유효하지 않은 문자열입니다.(빈 문자열/특수문자(빈칸 제외) 불가능, 1~99자 문자열만 가능)" + System.lineSeparator();
            }
        };
    }

    protected static int validateIdAndPages(String inputStr) {
        if (Pattern.matches("^[0-9]{1,4}$", inputStr)) {
            int input = Integer.parseInt(inputStr);
            if (0 < input && input < 5000) {
                return input;
            }
        }
        throw new ValidateException() {
            @Override
            public String getMessage() {
                return System.lineSeparator() + "[System] 유효하지 않은 숫자입니다.(1~4999 범위의 정수만 가능)" + System.lineSeparator();
            }
        };
    }

    protected static int validateSelectNum(int selectCount, String inputStr) {
        if (Pattern.matches("[1-9]", inputStr)) {
            int input = Integer.parseInt(inputStr);
            if (input <= selectCount) {
                return input;
            }
        }
        throw new ValidateException() {
            @Override
            public String getMessage() {
                return System.lineSeparator() + "[System] 유효하지 않은 선택 번호입니다. 선택지 안에서 번호를 입력해주세요." + System.lineSeparator();
            }
        };
    }
}