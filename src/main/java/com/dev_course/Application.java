package com.dev_course;

import com.dev_course.io_module.ConsoleBufferedReader;
import com.dev_course.io_module.ConsoleSystemWriter;
import com.dev_course.io_module.Reader;
import com.dev_course.io_module.Writer;
import com.dev_course.library.LibrarySystem;

public class Application {
    public static void main(String[] args) {
        Reader reader = new ConsoleBufferedReader();
        Writer writer = new ConsoleSystemWriter();

        LibrarySystem library = new LibrarySystem(reader, writer);

        library.run();
    }
}
