package com.dev_course.io_module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleLibraryReader implements LibraryReader {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private String sVal;

    private void next() {
        try {
            sVal = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String read() {
        next();
        return sVal;
    }

    @Override
    public String readOrDefault(String defaultValue) {
        try {
            next();
            return sVal;
        } catch (RuntimeException e) {
            return defaultValue;
        }
    }
}
