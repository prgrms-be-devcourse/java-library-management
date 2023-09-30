package com.example.library.validation;

public class InputValidator {

    public static int isNumber(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("숫자가 아닌 문자를 입력하셨습니다. 다시 입력해주세요. ");
        }

    }

    public static int isModeNumber(String input) {

        int modeNumber = isNumber(input);

        if (modeNumber == 1 || modeNumber == 2) {
            return modeNumber;
        }
        throw new NumberFormatException("모드 선택은 1번 혹은 2번만 가능합니다.");
    }

    public static int isMenuNumber(String input) {

        int menuNumber = isNumber(input);

        if (menuNumber < 8 && menuNumber > -1) {
            return menuNumber;
        }
        throw new NumberFormatException("메뉴 선택은 0번부터 7번까지 가능합니다.");
    }

}
