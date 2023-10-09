package com.programmers.cons;

public class Const {
    private static Long sequence = 0L;
    public static Long usingSequence(){
        Const.sequence++;
        return Const.sequence;
    }

    public static void resetSequence(){
        Const.sequence = 0L;
    }

}
