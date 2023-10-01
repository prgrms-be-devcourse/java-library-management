package com.dev_course.io_module;

public class ConsoleSystemWriter implements Writer {
    StringBuilder sb = new StringBuilder();

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

    @Override
    public void append(Object o) {
        sb.append(o);
    }

    @Override
    public void flush() {
        System.out.println(sb);
        sb.setLength(0);
    }
}
