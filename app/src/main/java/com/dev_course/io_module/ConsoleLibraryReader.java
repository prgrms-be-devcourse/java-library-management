package com.dev_course.io_module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class ConsoleLibraryReader implements LibraryReader {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private String str;

    private void next() throws IOException {
        str = br.readLine();
    }

    @Override
    public String read() throws IOException {
        next();
        return str;
    }

    @Override
    public String readOrElse(String defaultValue) {
        try {
            next();
        } catch (IOException ignored) {
            str = null;
        }

        return Objects.isNull(str) ? defaultValue : str;
    }
}
