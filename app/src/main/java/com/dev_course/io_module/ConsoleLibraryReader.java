package com.dev_course.io_module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleLibraryReader implements LibraryReader {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private String str;

    private void next() {
        try {
            str = br.readLine();
        } catch (IOException e) {
            str = "";
        }
    }

    @Override
    public String read() {
        next();
        return str;
    }

    @Override
    public String readOrElse(String defaultValue) {
        next();
        return str.isEmpty() ? defaultValue : str;
    }
}
