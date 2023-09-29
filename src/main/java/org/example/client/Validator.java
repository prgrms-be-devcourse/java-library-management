package org.example.client;

import org.example.packet.RequestData;

// 값의 유효성을 체크하고 유효하지 않으면 예외를 던지는 역할
// 통과하면 다시 그대로 내보낸다.(이 함수를 사용하는 입장에서 간결해보이기 위해)
public class Validator {
    public static RequestData validateBook(RequestData requestData, String[] input) {
        requestData.name = validateNameAndAuthor(input[0]);
        requestData.author = validateNameAndAuthor(input[1]);
//        if (input[2]) // 숫자인지 확인
        requestData.pages = validatePages(Integer.parseInt(input[2]));
        return requestData;
    }

    public static String validateNameAndAuthor(String input) {
        if (input.isEmpty() || 99 < input.length())
            throw new RuntimeException();
        return input;
    }

    public static int validateId(int input) {
        if (input < 0 || 4999 < input) {
            throw new RuntimeException();
        }
        return input;
    }

    public static int validatePages(int input) {
        if (input < 1 || 4999 < input) {
            throw new RuntimeException();
        }
        return input;
    }

    public static int validateSelectNum(int selectCount, int selectNum) {
        if (selectNum < 1 || selectCount < selectNum) {
            throw new RuntimeException();
        }
        return selectNum;
    }
}