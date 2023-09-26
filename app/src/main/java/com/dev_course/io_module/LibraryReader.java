package com.dev_course.io_module;

import java.io.IOException;

public interface LibraryReader {
    String read() throws IOException;
    String readOrElse(String defaultValue);
}
