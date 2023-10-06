package org.example.client;

import org.example.client.io.ConsoleIn;
import org.example.client.io.In;

import java.util.regex.Pattern;

public class Validator { // 두 가지 책임: 1.스캔, 2.검증 -> in이 바뀐다면? / 검증에 대한 역할만!
    private final In IN;

    public Validator(In in) {
        IN = in;
    }

    public Validator() {
        IN = ConsoleIn.getInstance();
    }

    public String scanAndValidateString() {
        String input = IN.scanLine().trim();

        if (Pattern.matches("[a-zA-Z0-9ㄱ-ㅎ가-힣 ]{1,99}$", input)) {
            return input;
        }
        throw new ValidateException() {
            @Override
            public String getMessage() {
                return System.lineSeparator() + "[System] 유효하지 않은 문자열입니다.(빈 문자열/특수문자 불가능. 1~99자 문자열만 가능.)" + System.lineSeparator();
            }
        };
    }

    public int scanAndValidateNumber() {
        String inputStr = IN.scanLine().trim();

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

    public int scanAndValidateSelection(int selectCount) {
        String inputStr = IN.scanLine().trim();

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