package com.dev_course.io_module;

public interface Writer {
    void print(Object o);

    void println();

    void println(Object o);

    void append(Object o);

    void flush();
}
