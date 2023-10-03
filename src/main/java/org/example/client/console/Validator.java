package org.example.client.console;

import org.example.packet.RequestData;

import java.util.regex.Pattern;

public class Validator {
    protected static RequestData validateBook(String[] input) {
        String name = validateNameAndAuthor(input[0]);
        String author = validateNameAndAuthor(input[1]);
        int pages = validateIdAndPages(input[2]);
        return new RequestData(name, author, pages);
    }

    protected static String validateNameAndAuthor(String input) {
        if (Pattern.matches("[a-zA-Z0-9ㄱ-ㅎ가-힣]*$", input) && !input.isEmpty() && input.length() < 100) {
            return input;
        }
        throw new ValidateException() {
            @Override
            public String getMessage() {
                return "\n[System] 유효하지 않은 문자열입니다.(빈 문자열/특수문자 불가능)\n";
            }
        };
    }

    protected static int validateIdAndPages(String inputStr) {
        if (Pattern.matches("^[0-9]*$", inputStr) && !inputStr.isEmpty()) {
            int input = Integer.parseInt(inputStr);
            if (0 < input && input < 5000) {
                return input;
            }
        }
        throw new ValidateException() {
            @Override
            public String getMessage() {
                return "\n[System] 유효하지 않은 숫자입니다.(1~4999 범위의 정수만 가능)\n";
            }
        };
    }

    protected static int validateSelectNum(int selectCount, String inputStr) {
        if (Pattern.matches("^[0-9]*$", inputStr) && !inputStr.isEmpty()) {
            int input = Integer.parseInt(inputStr);
            if (0 < input && input <= selectCount) {
                return input;
            }
        }
        throw new ValidateException() {
            @Override
            public String getMessage() {
                return "\n[System] 유효하지 않은 선택 번호입니다. 선택지 안에서 번호를 입력해주세요.\n";
            }
        };
    }
}