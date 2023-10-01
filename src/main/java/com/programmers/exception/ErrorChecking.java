package com.programmers.exception;

public class ErrorChecking {

    public static boolean checkNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean optionChecking(int menu) {
        return menu <= 8 && menu >= 1;
    }

    public static boolean modeChecking(int mode){
        return mode == 1 ||  mode ==2;
    }

    public static boolean minusChecking(int input) {
        return !(input < 0);
    }

}
