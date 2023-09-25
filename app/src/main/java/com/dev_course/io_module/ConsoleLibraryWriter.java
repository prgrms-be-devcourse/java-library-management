package com.dev_course.io_module;

public class ConsoleLibraryWriter implements LibraryWriter {
    StringBuilder sb = new StringBuilder();

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
