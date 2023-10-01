package com.dev_course.io_module;

public class ConsoleSystemWriter implements Writer {
    @Override
    public void print(Object o) {
        System.out.print(o);
    }

    @Override
    public void println() {
        System.out.println();
    }

    @Override
    public void println(Object o) {
        System.out.println(o);
    }
}
