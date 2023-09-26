package com.example.library.validation;

public class InputCheck {

    public static int isNumber(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }

    }

    public static int isModeNumber(String input) {

        int modeNumber = isNumber(input);

        if (modeNumber == 1 || modeNumber == 2) {
            return modeNumber;
        }
        throw new NumberFormatException();
    }

    public static int isMenuNumber(String input) {

        int menuNumber = isNumber(input);

        if (menuNumber < 8 && menuNumber > -1) {
            return menuNumber;
        }
        throw new NumberFormatException();
    }

}
