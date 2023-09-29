package org.example.client.console;

import org.example.packet.RequestData;

// 값의 유효성을 체크하고 유효하지 않으면 예외를 던지는 역할
// 통과하면 이 메서드를 사용하는 입장에서 간결해보이기 위해 다시 그대로 검사한 데이터를 내보낸다.
// int는 제외) String으로 받고 int로 내보낸다.
public class Validator {
    public static RequestData validateBook(RequestData requestData, String[] input) {
        requestData.name = validateName(input[0]);
        requestData.author = validateAuthor(input[1]);
        requestData.pages = validatePages(input[2]);
        return requestData;
    }

    public static String validateName(String input) {
        if (input.isEmpty() || 99 < input.length())
            throw new ValidateException() {
                @Override
                public String getMessage() {
                    return "\n[System] 유효하지 않은 이름 값입니다.\n";
                }
            };
        return input;
    }

    public static String validateAuthor(String input) {
        if (input.isEmpty() || 99 < input.length())
            throw new ValidateException() {
                @Override
                public String getMessage() {
                    return "\n[System] 유효하지 않은 저자 값입니다.\n";
                }
            };
        return input;
    }

    public static int validateId(String inputStr) {
        int input = Integer.parseInt(inputStr); // 숫자인지 확인
        if (input < 0 || 4999 < input) {
            throw new ValidateException() {
                @Override
                public String getMessage() {
                    return "\n[System] 유효하지 않은 id 값입니다.\n";
                }
            };
        }
        return input;
    }

    public static int validatePages(String inputStr) {
        int input = Integer.parseInt(inputStr); // 숫자인지 확인
        if (input < 1 || 4999 < input) {
            throw new ValidateException() {
                @Override
                public String getMessage() {
                    return "\n[System] 유효하지 않은 페이지 값입니다.\n";
                }
            };
        }
        return input;
    }

    public static int validateSelectNum(int selectCount, String selectNumStr) {
        int selectNum = Integer.parseInt(selectNumStr); // 숫자인지 확인
        if (selectNum < 1 || selectCount < selectNum) {
            throw new ValidateException() {
                @Override
                public String getMessage() {
                    return "\n[System] 유효하지 않은 선택 값입니다.\n";
                }
            };
        }
        return selectNum;
    }
}