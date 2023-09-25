package io;

import constant.Guide;
import constant.Selection;

public class Output {

    public static void printModeOptions() {
        Selection.printModeOptions();
    }

    public static void printFunctionOptions() {
        Selection.printFunctionOptions();
    }

    public static void printGuide(Guide guide) {
        System.out.println(guide.getGuide());
    }
}
